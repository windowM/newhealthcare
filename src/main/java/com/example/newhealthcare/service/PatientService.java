package com.example.newhealthcare.service;

import com.example.newhealthcare.dto.HomeResponseDTO;
import com.example.newhealthcare.dto.patientdto.PatientResponseDTO;
import com.example.newhealthcare.dto.ResultDTO;
import com.example.newhealthcare.entity.DandP;
import com.example.newhealthcare.entity.Doctor;
import com.example.newhealthcare.entity.Patient;
import com.example.newhealthcare.repository.DandPRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.PatientRepository;
import com.example.newhealthcare.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    @Autowired
    private final PatientRepository patientRepository;
    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final DandPRepository dandPRepository;
    @Autowired
    private final SensorRepository sensorRepository;


    public ResultDTO login(PatientResponseDTO patientResponseDTO){
        System.out.println("환자 로그인 실행중...");

        Optional<Patient> patient=patientRepository.findById(patientResponseDTO.getPatientId());

        patient.ifPresentOrElse(
            patient1 -> {
                if(patient1.getPassword().equals(patientResponseDTO.getPassword())){
                    System.out.println("로그인 성공!!!");
                    patientResponseDTO.setResult("Success");
                    patientResponseDTO.setContent("");
                }
                else{
                    System.out.println("비밀번호 입력 실패...");
                    patientResponseDTO.setResult("Fail");
                    patientResponseDTO.setContent("비밀번호 입력 실패...");
                }
            },
            ()->{
                System.out.println("존재하지 않는 아이디...");
                patientResponseDTO.setResult("Fail");
                patientResponseDTO.setContent("존재하지 않는 아이디...");
            }
        );
        ResultDTO resultDTO= ResultDTO.builder().
                result(patientResponseDTO.getResult()).
                content(patientResponseDTO.getContent()).
                build();

        return resultDTO;
    }

    public ResultDTO create(PatientResponseDTO patientResponseDTO){
        System.out.println("환자 회원가입 실행중...");
        Optional<Patient> findPatient=patientRepository.findById(patientResponseDTO.getPatientId());

        findPatient.ifPresentOrElse(
            patient1->{
                System.out.println("이미 있는 회원입니다.");
                patientResponseDTO.setResult("Fail");
                patientResponseDTO.setContent("이미 있는 회원입니다.");
            },
            ()->{
                Patient newPatient=Patient.builder().
                        patientId(patientResponseDTO.getPatientId()).
                        password(patientResponseDTO.getPassword()).
                        born(patientResponseDTO.getBorn()).
                        email(patientResponseDTO.getEmail()).
                        name(patientResponseDTO.getName()).
                        phone(patientResponseDTO.getPhone()).
                        build();
                patientRepository.save(newPatient);
                System.out.println("환자 회원가입 완료!!!");
                patientResponseDTO.setResult("Success");
                patientResponseDTO.setContent("환자 회원가입 완료!!!");

            }
        );
        ResultDTO resultDTO= ResultDTO.builder().
                result(patientResponseDTO.getResult()).
                content(patientResponseDTO.getContent()).
                build();
        return resultDTO;
    }

    public HomeResponseDTO home(String id){
        HomeResponseDTO homeResponseDTO;
        Optional<Patient> findPatient=patientRepository.findById(id);
        if(findPatient.isPresent()){
            Patient patient1=findPatient.get();
            System.out.println("홈화면 주인 :"+patient1);
            homeResponseDTO=HomeResponseDTO.builder().
                    name(patient1.getName()).
                    born(patient1.getBorn()).
                    email(patient1.getEmail()).
                    result("Success").
                    content("성공!!!!").
                    //dandPList(null).//dandpService
                    //sensor(null).//senseorService
                    build();
        }
        else{
            homeResponseDTO=HomeResponseDTO.builder().
                    result("Fail").
                    content("없는 ID")
                    .build();
        }
        return homeResponseDTO;
    }

    public ResultDTO connectCode(String id, String tcode){
        Doctor doctor=doctorRepository.findByCode(tcode);
        Optional<Patient> patient=patientRepository.findById(id);
        DandP dandP = dandPRepository.findByCode(tcode);
        String result="";
        String content="";

        if(doctor!=null){
            if(dandP ==null){
                DandP newDandP = null;
                newDandP=DandP.builder().
                        doctorId(doctor).
                        patientId(patient.get()).
                        code(tcode).
                        build();
                dandPRepository.save(newDandP);
                System.out.println("patient.get() : "+patient.get());
                System.out.println(newDandP.getPatientId());
                System.out.println(newDandP.getDoctorId());

                System.out.println("연결 완료!!!");
                result="Success";
            }
            else{
                System.out.println("이미 존재하는 코드 번호 입니다.");
                result="Fail";
                content="이미 존재하는 코드 번호 입니다.";
            }
        }
        else{
            System.out.println("코드와 연결된 의사가 없다.");
            result="Fail";
            content="코드와 연결된 의사가 없다.";
        }
        ResultDTO resultDTO = ResultDTO.builder().
                result(result).
                content(content).
                build();

        return resultDTO;
    }

}
