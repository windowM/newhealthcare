package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.network.request.DoctorApiRequest;
import com.example.newhealthcare.model.network.response.DoctorApiResponse;
import com.example.newhealthcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService implements CrudInterface<DoctorApiRequest, DoctorApiResponse> {
    @Autowired
    private final DoctorRepository doctorRepository;

    //의사 로그인
    public Header<DoctorApiResponse> login(Header<DoctorApiRequest> request){
        DoctorApiRequest doctorApiRequest=request.getData();
        Optional<Doctor> doctor=doctorRepository.findById(doctorApiRequest.getDoctorId());
        return (Header<DoctorApiResponse>) doctor.map(doctor1->{
            if(doctor1.getPassword().equals(doctorApiRequest.getPassword())){
                return response(doctor1);
            }else{
                return Header.ERROR("틀린 비밀번호 입니다.");
            }
        }).orElseGet(()->Header.ERROR("없는 회원 정보입니다."));
    }

    //의사 회원가입
    @Override
    public Header<DoctorApiResponse> create(Header<DoctorApiRequest> request) {
        //데이터 가져오기
        DoctorApiRequest doctorApiRequest=request.getData();
        Optional<Doctor> doctor=doctorRepository.findById(doctorApiRequest.getDoctorId());
        //생성하기기
        if(!doctor.isPresent()) {
            Doctor doctor1 = Doctor.builder().
                    doctorId(doctorApiRequest.getDoctorId()).
                    password(doctorApiRequest.getPassword()).
                    name(doctorApiRequest.getName()).
                    email(doctorApiRequest.getEmail()).
                    phone(doctorApiRequest.getPhone()).
                    major(doctorApiRequest.getMajor()).
                    build();

            Doctor newDoctor = doctorRepository.save(doctor1);
            return response(newDoctor);
        }else{
            return Header.ERROR("존재하는 회원");
        }
        //생성 데이터 return

    }

    //의사 정보 읽기
    @Override
    public Header<DoctorApiResponse> read(String id) {
        Optional<Doctor> doctor=doctorRepository.findById(id);

        return doctor
                .map(doctor1 -> response(doctor1))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    //의사 회원정보 업데이트
    @Override
    public Header<DoctorApiResponse> update(Header<DoctorApiRequest> request) {
        DoctorApiRequest doctorApiRequest=request.getData();

        Optional<Doctor> doctor=doctorRepository.findById(doctorApiRequest.getDoctorId());
        return doctor.map(doctor1->{
            doctor1.setPassword(doctorApiRequest.getPassword())
                    .setEmail(doctorApiRequest.getEmail())
                    .setMajor(doctorApiRequest.getMajor())
                    .setName(doctorApiRequest.getName())
                    .setPhone(doctorApiRequest.getPhone());
            return doctor1;
        })
        .map(doctor1->doctorRepository.save(doctor1))
        .map(updateDoctor->response(updateDoctor))
        .orElseGet(()->Header.ERROR("의사정보 없음"));
    }

    //의사 코드번호 발급
    public Header updateCode(Header<DoctorApiRequest> request){
        DoctorApiRequest doctorApiRequest=request.getData();
        Optional<Doctor> doctor=doctorRepository.findById(doctorApiRequest.getDoctorId());

        return doctor.map(doctor1->{
            doctor1.setCode(doctorApiRequest.getCode());
            doctorRepository.save(doctor1);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("의사정보 없음"));
    }

    //의사 회원 삭제
    @Override
    public Header delete(String id) {
        Optional<Doctor> doctor=doctorRepository.findById(id);

        return doctor.map(doctor1->{
            doctorRepository.delete(doctor1);
            return Header.OK();
        })
        .orElseGet(()->Header.ERROR("의사정보 없음"));
    }

    private Header<DoctorApiResponse> response(Doctor doctor){
        DoctorApiResponse doctorApiResponse=DoctorApiResponse.builder().
                doctorId(doctor.getDoctorId()).
                password(doctor.getPassword()).  //암호화 필요
                name(doctor.getName()).
                email(doctor.getEmail()).
                phone(doctor.getPhone()).
                major(doctor.getMajor()).
                build();

        //header + data => return
        return Header.OK(doctorApiResponse);
    }

//    public DoctorResponseDTO login(DoctorResponseDTO doctorResponseDTO){
//        System.out.println("의사 로그인 실행중...");
//
//        Optional<Doctor> findDoctor=doctorRepository.findById(doctorResponseDTO.getDoctorId());
//
//        findDoctor.ifPresentOrElse(
//            //객체가 있으면
//            doctor1->{
//                if (doctor1.getPassword().equals(doctorResponseDTO.getPassword())) {
//                    System.out.println("로그인 성공!!!");
//                    doctorResponseDTO.setDoctorId(doctor1.getDoctorId());
//                    doctorResponseDTO.builder().
//                            doctorId(doctor1.getDoctorId()).
//                            password(doctor1.getPassword()).
//                            major(doctor1.getMajor()).
//                            email(doctor1.getEmail()).
//                            code(doctor1.getCode()).
//                            name(doctor1.getName()).
//                            result("Success").
//                            build();
//                } else {
//                    System.out.println("비밀번호 입력 실패...");
//                    doctorResponseDTO.setResult("Fail");
//                }
//
//            },
//            //객체가 없으면
//            ()->{
//                System.out.println("존재하지 않는 아이디...");
//                doctorResponseDTO.setResult("Fail");
//            }
//        );
//
//        return doctorResponseDTO;
//    }
//
//
//    public ResultDTO create(DoctorResponseDTO doctorResponseDTO){
//        System.out.println("의사 회원가입 실행중....");
//        Optional<Doctor> findDoctor=doctorRepository.findById(doctorResponseDTO.getDoctorId());
//
//        findDoctor.ifPresentOrElse(
//                doctor1->{
//                    System.out.println("이미 있는 회원입니다.");
//                    doctorResponseDTO.setResult("Fail");
//                    doctorResponseDTO.setContent("이미 있는 회원입니다.");
//                },
//                ()->{
//                    Doctor newDoctor=Doctor.builder().
//                            doctorId(doctorResponseDTO.getDoctorId()).
//                            password(doctorResponseDTO.getPassword()).
//                            name(doctorResponseDTO.getName()).
//                            email(doctorResponseDTO.getEmail()).
//                            phone(doctorResponseDTO.getPhone()).
//                            major(doctorResponseDTO.getMajor()).
//                            build();
//                    doctorRepository.save(newDoctor);
//                    System.out.println("의사 회원가입 완료!!!");
//                    doctorResponseDTO.setResult("Success");
//                    doctorResponseDTO.setContent("이미 있는 회원입니다.");
//                }
//        );
//        ResultDTO resultDTO= ResultDTO.builder().
//                result(doctorResponseDTO.getResult()).
//                content(doctorResponseDTO.getContent()).
//                build();
//        return resultDTO;
//
//    }
}
