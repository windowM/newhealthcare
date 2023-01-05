package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.patientdto.PatientResponseDTO;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.entity.DandP;
import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.network.request.DoctorApiRequest;
import com.example.newhealthcare.model.network.response.DoctorApiResponse;
import com.example.newhealthcare.repository.DandPRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService implements CrudInterface<DoctorApiRequest, DoctorApiResponse> {
    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final DandPRepository dandPRepository;

    //의사 로그인
    public Header login(Header<DoctorApiRequest> request){
        DoctorApiRequest doctorApiRequest=request.getData();
        Optional<Doctor> doctor=doctorRepository.findById(doctorApiRequest.getDoctorId());
        return doctor.map(doctor1->{
            if(doctor1.getPassword().equals(doctorApiRequest.getPassword())){
                return Header.OK();
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

    //response의 구현 이유를 못살림...
    //로그인 시 해당 의사 정보 보내주기
    @Override
    public Header<DoctorApiResponse> read(String id) {
        Optional<Doctor> doctor=doctorRepository.findById(id);
        return doctor
                .map(doctor1 -> {
                    List<PatientResponseDTO> patientResponseDTOS = new ArrayList<PatientResponseDTO>();
                    doctor1.getDandpList().stream().forEach(dandP -> {
                        PatientResponseDTO patientResponseDTO=PatientResponseDTO.builder()
                                .born(dandP.getPatientId().getBorn())
                                .name(dandP.getPatientId().getName())
                                .phone(dandP.getPatientId().getPhone())
                                .build();
                        patientResponseDTOS.add(patientResponseDTO);
                    });

                    DoctorApiResponse doctorApiResponse=DoctorApiResponse.builder()
                            .doctorId(doctor1.getDoctorId())
                            .email(doctor1.getEmail())
                            .name(doctor1.getName())
                            .major(doctor1.getMajor())
                            .code(doctor1.getCode())
                            .patientId(patientResponseDTOS)
                            .build();
                    return Header.OK(doctorApiResponse);
                })
                .orElseGet(()->Header.ERROR("존재하지 않는 회원"));
    }


    //업데이트에서만 response를 사용하는데 다시 생각해봐야 할듯
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
    public Header updateCode(String id,Header<DoctorApiRequest> request){
        DoctorApiRequest doctorApiRequest=request.getData();
        Optional<Doctor> doctor=doctorRepository.findById(id);

        return doctor.map(doctor1->{
            Optional<DandP> dandp=dandPRepository.findByCode(doctorApiRequest.getCode());
            //기존 연결된 코드번호가 요청 되었을때 예외 처리
            if(dandp.isPresent()){
                return Header.ERROR("존재하는 코드 번호입니다.");
            }else {
                doctor1.setCode(doctorApiRequest.getCode());
                doctorRepository.save(doctor1);
                return Header.OK();
            }
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

    //return response
    private Header<DoctorApiResponse> response(Doctor doctor){
        DoctorApiResponse doctorApiResponse=DoctorApiResponse.builder().
                doctorId(doctor.getDoctorId()).
                password(doctor.getPassword()).  //암호화 필요
                name(doctor.getName()).
                email(doctor.getEmail()).
                phone(doctor.getPhone()).
                major(doctor.getMajor()).
                //patientId(doctor.getDandpList()).
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
