package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.PatientResponseDTO;
import com.example.newhealthcare.dto.ReservationDoctorDTO;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.entity.DandP;
import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.entity.Reservation;
import com.example.newhealthcare.model.network.request.doctor.DoctorApiRequest;
import com.example.newhealthcare.model.network.response.doctor.DoctorApiResponse;
import com.example.newhealthcare.repository.DandPRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private final ReservationRepository reservationRepository;

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
                    gender(doctorApiRequest.getGender()).
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
                                .patientId(dandP.getPatientId().getPatientId())
                                .born(dandP.getPatientId().getBorn())
                                .name(dandP.getPatientId().getName())
                                .phone(dandP.getPatientId().getPhone())
                                .gender(dandP.getPatientId().getGender())
                                .build();
                        patientResponseDTOS.add(patientResponseDTO);
                    });

                    //오늘의 예약 정보
                    String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                    System.out.println("오늘 날짜 : "+now);

                    List<Reservation> reservationList=reservationRepository.findBySelDoctorId(doctor1.getDoctorId());
                    List<ReservationDoctorDTO> todayResList=new ArrayList<ReservationDoctorDTO>();
                    if(!reservationList.isEmpty()){
                        for(int i=0;i<reservationList.size();i++) {
                            if (reservationList.get(i).getResDate().equals(now)) {
                                PatientResponseDTO patientResponseDTO = PatientResponseDTO.builder()
                                        .patientId(reservationList.get(i).getPatientId().getPatientId())
                                        .born(reservationList.get(i).getPatientId().getBorn())
                                        .gender(reservationList.get(i).getPatientId().getGender())
                                        .name(reservationList.get(i).getPatientId().getName())
                                        .phone(reservationList.get(i).getPatientId().getPhone())
                                        .build();

                                ReservationDoctorDTO resDTO = ReservationDoctorDTO.builder()
                                        .resNum(reservationList.get(i).getResNum())
                                        .selDoctorId(reservationList.get(i).getSelDoctorId())
                                        .patientId(patientResponseDTO)
                                        .contents(reservationList.get(i).getContents())
                                        .resDate(reservationList.get(i).getResDate())
                                        .resTime(reservationList.get(i).getResTime())
                                        .build();

                                todayResList.add(resDTO);
                            }
                            else{ continue;}
                        }
                    }else{
                        System.out.println("오늘의 예약이 비어있다.");
                    }

                    DoctorApiResponse doctorApiResponse=DoctorApiResponse.builder()
                            .doctorId(doctor1.getDoctorId())
                            .email(doctor1.getEmail())
                            .name(doctor1.getName())
                            .gender(doctor1.getGender())
                            .major(doctor1.getMajor())
                            .code(doctor1.getCode())
                            .patientId(patientResponseDTOS)
                            .todayResList(todayResList)
                            .build();
                    return Header.OK(doctorApiResponse);
                })
                .orElseGet(()->Header.ERROR("존재하지 않는 의사정보"));
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
                gender(doctor.getGender()).
                name(doctor.getName()).
                email(doctor.getEmail()).
                phone(doctor.getPhone()).
                major(doctor.getMajor()).
                //patientId(doctor.getDandpList()).
                build();

        //header + data => return
        return Header.OK(doctorApiResponse);
    }

}
