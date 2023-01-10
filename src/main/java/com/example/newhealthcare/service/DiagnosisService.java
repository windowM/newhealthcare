package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.DoctorDiagnosisResponseDTO;
import com.example.newhealthcare.dto.PatientDiagnosisResponseDTO;
import com.example.newhealthcare.model.entity.Diagnosis;
import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.entity.Patient;
import com.example.newhealthcare.model.network.request.doctor.DoctorDiagnosisApiRequest;
import com.example.newhealthcare.model.network.response.doctor.DoctorDiagnosisApiResponse;
import com.example.newhealthcare.model.network.response.patient.PatientDiagnosisApiResponse;
import com.example.newhealthcare.repository.DiagnosisRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisService {
    @Autowired
    private DiagnosisRepository diagnosisRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    //진단 리스트 불러오기
    public Header<?> read(String user, String id){
        //의사라면
        if(user.equals("doctor")) {
            Optional<Doctor> diaDoctor = doctorRepository.findById(id);
            return diaDoctor.map(doctor -> {
                List<DoctorDiagnosisResponseDTO> doctorDiagnosisResponseDTOS =new ArrayList<DoctorDiagnosisResponseDTO>();;
                doctor.getDiagnosisList().stream().forEach(diagnosis1 -> {
                    DoctorDiagnosisResponseDTO doctorDiagnosisResponseDTO = DoctorDiagnosisResponseDTO.builder()
                            .diaNum(diagnosis1.getDiaNum())
                            .selectPatientId(diagnosis1.getSelPatientId())
                            .disease(diagnosis1.getDisease())
                            .contents(diagnosis1.getContents())
                            .diaDate(diagnosis1.getDiaDate())
                            .build();
                    doctorDiagnosisResponseDTOS.add(doctorDiagnosisResponseDTO);

                });
                DoctorDiagnosisApiResponse doctorDiagnosisApiResponse = DoctorDiagnosisApiResponse.builder()
                        .doctorId(doctor.getDoctorId())
                        .diagnosisList(doctorDiagnosisResponseDTOS)
                        .build();
                return Header.OK(doctorDiagnosisApiResponse);
            }).orElseGet(()->Header.ERROR("의사 정보가 없음"));
        }
        //환자라면
        else if(user.equals("patient")){
            Optional<Patient> selPatient = patientRepository.findById(id);
            return selPatient.map(patient -> {
                List<PatientDiagnosisResponseDTO> patientDiagnosisResponseDTOS=new ArrayList<PatientDiagnosisResponseDTO>();
                patient.getDandpList().stream().forEach(dandP -> {
                    dandP.getDoctorId().getDiagnosisList().stream().forEach(diagnosis -> {
                        if(diagnosis.getSelPatientId().equals(patient.getPatientId())){
                            PatientDiagnosisResponseDTO patientDiagnosisResponseDTO= PatientDiagnosisResponseDTO.builder()
                                    .diaDoctorId(diagnosis.getDoctorId().getDoctorId())
                                    .disease(diagnosis.getDisease())
                                    .contents(diagnosis.getContents())
                                    .diaDate(diagnosis.getDiaDate())
                                    .build();
                            patientDiagnosisResponseDTOS.add(patientDiagnosisResponseDTO);
                        }
                    });
                });
                PatientDiagnosisApiResponse patientDiagnosisApiResponse=PatientDiagnosisApiResponse.builder()
                        .patientId(patient.getPatientId())
                        .diagnosisList(patientDiagnosisResponseDTOS)
                        .build();
                return Header.OK(patientDiagnosisApiResponse);

            }).orElseGet(()->Header.ERROR("환자 정보가 없음"));
        }
        //모두 아니면
        else{
            return Header.ERROR("who...");
        }
    }


    // 지금은 연결되지 않은 환자에 대한 예외처리 안함
    public Header create(String user,String id,Header<DoctorDiagnosisApiRequest> request) {
        if(user.equals("doctor")) {
            System.out.println("doctor:!!!");
            DoctorDiagnosisApiRequest doctorDiagnosisApiRequest = request.getData();
            Optional<Doctor> diaDoctor = doctorRepository.findById(id);
            Optional<Patient> selPatient = patientRepository.findById(doctorDiagnosisApiRequest.getSelectPatientId());
            if (diaDoctor.isPresent() && selPatient.isPresent()) {
                Doctor doctor1 = diaDoctor.get();

                Diagnosis diagnosis1 = Diagnosis.builder()
                        .doctorId(doctor1)
                        .selPatientId(doctorDiagnosisApiRequest.getSelectPatientId())
                        .disease(doctorDiagnosisApiRequest.getDisease())
                        .contents(doctorDiagnosisApiRequest.getContents())
                        .diaDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                        .build();

                diagnosisRepository.save(diagnosis1);

                return Header.OK();
            } else {
                return Header.ERROR("의사/환자 정보가 없다.");
            }
        }
        else{
            return Header.ERROR("patient post요청 ");
        }
    }

}
