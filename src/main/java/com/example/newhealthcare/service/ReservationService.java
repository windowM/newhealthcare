package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.DoctorResponseDTO;
import com.example.newhealthcare.dto.ReservationInfoDTO;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.entity.DandP;
import com.example.newhealthcare.model.entity.Patient;
import com.example.newhealthcare.model.entity.Reservation;
import com.example.newhealthcare.model.network.request.ReservationApiRequest;
import com.example.newhealthcare.model.network.response.*;
import com.example.newhealthcare.repository.DandPRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.PatientRepository;
import com.example.newhealthcare.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService implements CrudInterface<ReservationApiRequest, ReservationApiResponse> {
    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final PatientRepository patientRepository;
    @Autowired
    private final DandPRepository dandPRepository;

    @Override
    public Header<ReservationApiResponse> create(Header<ReservationApiRequest> request) {
        ReservationApiRequest requestData=request.getData();
        Optional<Patient> patient=patientRepository.findById(requestData.getPatientId());
        List<Reservation> reservation=
                reservationRepository.findBySelDoctorId(requestData.getSelDoctorId());

        if(!reservation.isEmpty()|| reservation!=null) {
            for(int i=0; i< reservation.size();i++){
                if(reservation.get(i).getResDate().equals(requestData.getResDate())){
                    return Header.ERROR("이미 예약된 시간입니다.");
                }
            }
        }
        Reservation reservation1= Reservation.builder()
                .patientId(patient.get())
                .selDoctorId(requestData.getSelDoctorId())
                .contents(requestData.getContents())
                .resTime(requestData.getResTime())
                .resDate(requestData.getResDate())
                .resDate(requestData.getResDate())
                .build();
        reservationRepository.save(reservation1);

        return Header.OK();

    }

    @Override
    public Header<ReservationApiResponse> read(String id) {
        return null;
    }

    //예약시 connect된 의사들 리스트
//    public Header<ConnectListApiResponse> showDocList(String id){
//        Optional<Patient> patient=patientRepository.findById(id);
//        List<DandP> dandPS= patient.get().getDandpList();
//        List<DoctorResponseDTO> doctorResponseDTOS=new ArrayList<DoctorResponseDTO>();
//
//        for(int i=0;i<dandPS.size();i++) {
//            DoctorResponseDTO doctorResponseDTO= DoctorResponseDTO.builder()
//                    .id(dandPS.get(i).getDoctorId().getDoctorId())
//                    .name(dandPS.get(i).getDoctorId().getName())
//                    .gender(dandPS.get(i).getDoctorId().getGender())
//                    .major(dandPS.get(i).getDoctorId().getMajor())
//                    .build();
//            doctorResponseDTOS.add(doctorResponseDTO);
//        }
//
//        ConnectListApiResponse doctorList= ConnectListApiResponse.builder()
//                .doctor(doctorResponseDTOS)
//                .build();
//
//        return Header.OK(doctorList);
//    }

    //예약페이지 들어갈때 보내줄 데이터 (connect의사리스트 포함)
    public Header<ReservationHomeApiResponse> show(String id){
        Optional<Patient> patient=patientRepository.findById(id);
        List<DoctorResponseDTO> doctorResponseDTOS=new ArrayList<DoctorResponseDTO>();
        List<ReservationInfoDTO> reservationInfoDTOS=new ArrayList<ReservationInfoDTO>();

        if(patient.isPresent()){
            List<DandP> dandPS= patient.get().getDandpList();
            List<Reservation> reservation= reservationRepository.findByPatientId(patient.get());

            if(dandPS.isEmpty()) {
                System.out.println("danps.isempty()");
            }else{
                //connect의사 리스트들 보내줄때
                for(int i=0;i< dandPS.size();i++){

                    DoctorResponseDTO doctorResponseDTO= DoctorResponseDTO.builder()
                            .id(dandPS.get(i).getDoctorId().getDoctorId())
                            .name(dandPS.get(i).getDoctorId().getName())
                            .gender(dandPS.get(i).getDoctorId().getGender())
                            .major(dandPS.get(i).getDoctorId().getMajor())
                            .build();

                    doctorResponseDTOS.add(doctorResponseDTO);
                }

            }
            if(reservation.isEmpty()) {
                System.out.println("reservation.isempty()");
            }else{
                //예약정보 보내줄때
                for(int i=0;i< reservation.size();i++){
                    Reservation reservation1 = reservation.get(i);
                    ReservationInfoDTO reservationInfoDTO = ReservationInfoDTO.builder()
                            .doctorName(reservation1.getSelDoctorId())
                            .resDate(reservation1.getResDate())
                            .resTime(reservation1.getResTime())
                            .build();
                    reservationInfoDTOS.add(reservationInfoDTO);
                }
            }
        }
        ReservationHomeApiResponse response=
                ReservationHomeApiResponse.builder()
                        .doctor(doctorResponseDTOS)
                        .reservationList(reservationInfoDTOS)
                        .build();

        System.out.println("3:"+response);
        return Header.OK(response);
    }

    //예약시 가능한 의사별 시간대를 return
//    public Header<ReservationDateApiResponse> showDate(String id){
//
//    }

    @Override
    public Header<ReservationApiResponse> update(Header<ReservationApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(String id) {
        return null;
    }
}
