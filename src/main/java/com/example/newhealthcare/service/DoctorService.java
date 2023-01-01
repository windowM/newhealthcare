package com.example.newhealthcare.service;

import com.example.newhealthcare.dto.doctordto.DoctorResponseDTO;
import com.example.newhealthcare.dto.ResultDTO;
import com.example.newhealthcare.entity.Doctor;
import com.example.newhealthcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {
    @Autowired
    private final DoctorRepository doctorRepository;

    public DoctorResponseDTO login(DoctorResponseDTO doctorResponseDTO){
        System.out.println("의사 로그인 실행중...");

        Optional<Doctor> findDoctor=doctorRepository.findById(doctorResponseDTO.getDoctorId());

        findDoctor.ifPresentOrElse(
            //객체가 있으면
            doctor1->{
                if (doctor1.getPassword().equals(doctorResponseDTO.getPassword())) {
                    System.out.println("로그인 성공!!!");
                    doctorResponseDTO.setDoctorId(doctor1.getDoctorId());
                    doctorResponseDTO.builder().
                            doctorId(doctor1.getDoctorId()).
                            password(doctor1.getPassword()).
                            major(doctor1.getMajor()).
                            email(doctor1.getEmail()).
                            code(doctor1.getCode()).
                            name(doctor1.getName()).
                            result("Success").
                            build();
                } else {
                    System.out.println("비밀번호 입력 실패...");
                    doctorResponseDTO.setResult("Fail");
                }

            },
            //객체가 없으면
            ()->{
                System.out.println("존재하지 않는 아이디...");
                doctorResponseDTO.setResult("Fail");
            }
        );

        return doctorResponseDTO;
    }


    public ResultDTO create(DoctorResponseDTO doctorResponseDTO){
        System.out.println("의사 회원가입 실행중....");
        Optional<Doctor> findDoctor=doctorRepository.findById(doctorResponseDTO.getDoctorId());

        findDoctor.ifPresentOrElse(
                doctor1->{
                    System.out.println("이미 있는 회원입니다.");
                    doctorResponseDTO.setResult("Fail");
                    doctorResponseDTO.setContent("이미 있는 회원입니다.");
                },
                ()->{
                    Doctor newDoctor=Doctor.builder().
                            doctorId(doctorResponseDTO.getDoctorId()).
                            password(doctorResponseDTO.getPassword()).
                            name(doctorResponseDTO.getName()).
                            email(doctorResponseDTO.getEmail()).
                            phone(doctorResponseDTO.getPhone()).
                            major(doctorResponseDTO.getMajor()).
                            build();
                    doctorRepository.save(newDoctor);
                    System.out.println("의사 회원가입 완료!!!");
                    doctorResponseDTO.setResult("Success");
                    doctorResponseDTO.setContent("이미 있는 회원입니다.");
                }
        );
        ResultDTO resultDTO= ResultDTO.builder().
                result(doctorResponseDTO.getResult()).
                content(doctorResponseDTO.getContent()).
                build();
        return resultDTO;

    }
}
