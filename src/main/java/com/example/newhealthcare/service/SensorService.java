package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.SensorDTO;
import com.example.newhealthcare.model.entity.Patient;
import com.example.newhealthcare.model.entity.Sensor;
import com.example.newhealthcare.model.network.request.sensor.SensorApiRequest;
import com.example.newhealthcare.repository.PatientRepository;
import com.example.newhealthcare.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorService {

    @Autowired
    private final SensorRepository sensorRepository;

    @Autowired
    private final PatientRepository patientRepository;

    public Header create(Header<SensorApiRequest> request) throws IOException {
        SensorApiRequest request1=request.getData();
        Optional<Patient> patient=patientRepository.findById(request1.getPatientId());
        if(patient.isPresent()) {
            Sensor sensor = Sensor.builder().
                    patientId(patient.get()).
                    avgBpm(request1.getAvgBpm()).
                    hBpm(request1.getHighBpm()).
                    lBpm(request1.getLowBpm()).
                    avgSpo2(request1.getAvgSpo2()).
                    hSpo2(request1.getHighSpo2()).
                    lSpo2(request1.getLowSpo2()).
                    avgTemp(request1.getAvgTemp()).
                    hTemp(request1.getHighTemp()).
                    lTemp(request1.getLowTemp()).
                    mTime(request1.getMeasureTime()).
                    mDate(request1.getMeasureDate()).
                    build();

            SensorDTO sensorDTO= SensorDTO.builder().
                    avg_temp(request1.getAvgTemp()).
                    avg_bpm(request1.getAvgBpm()).
                    avg_spo2(request1.getAvgSpo2()).
                    build();

            Sensor savedSensor =sensorRepository.save(sensor);
            Long sequenceValue=savedSensor.getSq();

            String result1=predict(sequenceValue,patient.get().getPatientId(),sensorDTO);

            System.out.println("important1!!!! : "+result1);

            return Header.OK();
        }
        else{
            return Header.ERROR("회원이 존재하지 않습니다.");
        }
    }

    public String predict(Long sequence,String userID,SensorDTO inputData) throws IOException {
        String userName =userID;
        Long SequenceValue=sequence;
//        InputData inputData = request.getInputData();

        // 해당 사용자의 모델 파일이 존재하는지 확인
        boolean isModelExist = checkModelExist(userName);

        if (isModelExist) {
            // 모델이 존재하는 경우, 모델을 로드하여 예측 수행
            System.out.println("모델이 존재합니다!!!!");
            executePythonScript("C:/healthcare_AI/predictModule.py", userName, inputData,SequenceValue);
//            List<String> answerList = new ArrayList<>();
//
//            answerList.add(executePythonScript(userName+"_model1.pkl", userName, avgSensorData));
//            answerList.add(executePythonScript(userName+"_model2.pkl", userName, avgSensorData));
//            answerList.add(executePythonScript(userName+"_model3.pkl", userName, avgSensorData));
//            return answerList.toString();
            return "true";
        } else {
            // 모델이 존재하지 않는 경우, 모델 학습 후 예측 수행
            System.out.println("모델이 없습니다...");
            if(trainAndSaveModel(userName)) {
                System.out.println("모델 생성 완료.. 예측 진행");
                executePythonScript("C:/healthcare_AI/predictModule.py", userName, inputData,SequenceValue);

//                List<String> answerList = new ArrayList<>();
//                answerList.add(executePythonScript(userName+"_model1.pkl", userName, avgSensorData));
//                answerList.add(executePythonScript(userName+"_model2.pkl", userName, avgSensorData));
//                answerList.add(executePythonScript(userName+"_model3.pkl", userName, avgSensorData));
//                return answerList.toString();
                return "true";
            }else{
                System.out.println("모델 생성 실패");
                return "false";
            }
        }
    }

    private boolean checkModelExist(String userName) {
        // 모델 파일 존재 여부 확인 로직 구현
        String modelFilePath1 = "C:/healthcare_AI/"+userName+"_model1.pkl";
        String modelFilePath2 = "C:/healthcare_AI/"+userName+"_model2.pkl";
        String modelFilePath3 = "C:/healthcare_AI/"+userName+"_model3.pkl";
        File modelFile1 = new File(modelFilePath1);
        File modelFile2 = new File(modelFilePath2);
        File modelFile3 = new File(modelFilePath3);

        //3가지 스크립트 파일이 모두 존재 한다면 true
        if(modelFile1.exists() && modelFile2.exists() && modelFile3.exists()){
            return true;
        }
        return false;
    }

    private boolean trainAndSaveModel(String userName) throws IOException {
        // 모델 학습 및 저장 로직 구현
        // 파이썬 스크립트를 실행하여 모델 학습 후 저장
        String pythonScriptPath = "C:/healthcare_AI/myModule.py";
        String s=executePythonScript(pythonScriptPath, userName);
        return true;
    }

    private String executePythonScript(String scriptName, String userName) throws IOException {
        String venvPath = "C:/healthcare_AI/myenv"; // 가상환경 디렉토리 경로
        String pythonInterpreterPath=venvPath+"/Scripts/python.exe";
        String pythonScriptPath = scriptName;

        // 파이썬 스크립트 실행 명령어 설정
        List<String> command = new ArrayList<>();
        command.add("python");
        command.add(pythonScriptPath);
        command.add(pythonInterpreterPath);
        command.add(venvPath + "/Scripts/activate.bat");
        command.add(userName);

        // 프로세스 빌더 생성
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        // 프로세스 실행
        Process process = processBuilder.start();

        // 파이썬 스크립트의 출력 읽기
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                System.out.println("읽어온 line : " + line);
            }
        }finally {
            try{
                if(reader!=null){
                    reader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return output.toString();
    }

    private String executePythonScript(String scriptName, String userName, SensorDTO inputData,Long sequence) throws IOException {
        List<Integer> outputList = new ArrayList<>();
        Long sequenceValue=sequence;

        String venvPath = "C:/healthcare_AI/myenv"; // 가상환경 디렉토리 경로
        String pythonInterpreterPath=venvPath+"/Scripts/python.exe";
        String pythonScriptPath = scriptName;

        String avg_bpm= String.valueOf(inputData.getAvg_bpm());
        String avg_temp= String.valueOf(inputData.getAvg_temp());
        String avg_spo2= String.valueOf(inputData.getAvg_spo2());

        // 파이썬 스크립트 실행 명령어 설정
        List<String> command = new ArrayList<>();
        command.add("python");
        command.add(pythonScriptPath);
        command.add(pythonInterpreterPath);
        command.add(venvPath + "/Scripts/activate.bat");
        command.add(userName);
        command.add(avg_bpm);
        command.add(avg_temp);
        command.add(avg_spo2);

        // 프로세스 빌더 생성
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        // 프로세스 실행
        Process process = processBuilder.start();

        // 파이썬 스크립트의 출력 읽기
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        StringBuilder output = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                outputList.add(Integer.parseInt(line));
                System.out.println("읽어온 line2 : " + line);
            }
            Optional<Sensor> checkSensor=sensorRepository.findById(sequenceValue);
            if(checkSensor.isPresent()){
                Sensor updateSensor=checkSensor.get();
                updateSensor.setStatusBpm(outputList.get(0));
                updateSensor.setStatusTemp(outputList.get(1));
                updateSensor.setStatusSpo2(outputList.get(2));
                sensorRepository.save(updateSensor);
            }
        }finally {
            try{
                if(reader!=null){
                    reader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return outputList.toString();
    }
}
