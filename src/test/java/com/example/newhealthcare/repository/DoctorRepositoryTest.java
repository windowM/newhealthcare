package com.example.newhealthcare.repository;

import com.example.newhealthcare.NewhealthcareApplicationTests;
import com.example.newhealthcare.entity.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Optional;

public class DoctorRepositoryTest extends NewhealthcareApplicationTests {

    @Autowired
    private DoctorRepository doctorRepository;


    @Test
    public void create(){
        String id="cccc";
        String password="1234";
        String email="cccc@naver.com";
        String name="Lee";
        String phone="010-3333-3333";
        String major="치과";
        String code="";

        Doctor doctor1=Doctor.builder().
                doctorId(id).
                password(password).
                email(email).
                name(name).
                phone(phone).
                major(major).
                code(code).
                build();

        Doctor newDoctor = doctorRepository.save(doctor1);
        System.out.println("newDoc:" + newDoctor);
    }

    @Test
    @Transactional
    public void read(){
        //doctorRepository.findAll(); // doctor테이블 리스트 모두 들고옴
        Optional<Doctor> doctor=doctorRepository.findById("aaaa"); //optional:있을수도 없을수도 있다,

        doctor.ifPresent(docotor1->{
            System.out.println("doctor : "+docotor1.getDoctorId());
        });
    }
//    @Test
//    @Transactional
//    public void read(@RequestParam String id){
//        //doctorRepository.findAll(); // doctor테이블 리스트 모두 들고옴
//        Optional<Doctor> doctor=doctorRepository.findById(id); //optional:있을수도 없을수도 있다,
//
//        doctor.ifPresent(docotor1->{
//            System.out.println("doctor : "+docotor1.getSq());
//        });
//    }

    @Test
    public void update(){
        Optional<Doctor> doctor=doctorRepository.findById("aaaa"); //optional:있을수도 없을수도 있다,

        doctor.ifPresent(docotor1->{
            doctorRepository.save(docotor1);
        });

    }

    @Test
    public void delete(){

    }

}
