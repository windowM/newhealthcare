package com.example.newhealthcare.service;

import com.example.newhealthcare.dto.HomeResponseDTO;
import com.example.newhealthcare.dto.patientdto.PatientResponseDTO;
import com.example.newhealthcare.dto.ResultDTO;
import com.example.newhealthcare.entity.Doctor;
import com.example.newhealthcare.entity.Patient;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    @Autowired
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

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
        Optional<Patient> findPatient=patientRepository.findById(id);
        HomeResponseDTO homeResponseDTO=null;
        return homeResponseDTO;
    }

    public ResultDTO connectCode(String id, String code){
        Doctor doctor=doctorRepository.findByCode(code);
        System.out.println("doctor 정보 :"+doctor);
        ResultDTO resultDTO= ResultDTO.builder().result("Success").content("").build();
        return resultDTO;
    }

}
