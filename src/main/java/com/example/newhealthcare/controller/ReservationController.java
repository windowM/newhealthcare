package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.ReservationDoctorDTO;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.entity.Reservation;
import com.example.newhealthcare.model.network.request.reservation.ReservationApiRequest;
import com.example.newhealthcare.model.network.request.ReservationDateApiRequest;
import com.example.newhealthcare.model.network.response.reservation.ReservationApiResponse;
import com.example.newhealthcare.model.network.response.reservation.ReservationDateApiResponse;
import com.example.newhealthcare.model.network.response.reservation.ReservationHomePApiResponse;
import com.example.newhealthcare.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ReservationController implements CrudInterface<ReservationApiRequest, ReservationApiResponse> {

    @Autowired
    private ReservationService reservationService;

    //환자 : 예약 화면
    @GetMapping("/patient/{id}/reservation")
    public Header<ReservationHomePApiResponse> showDocList(@PathVariable String id){
        return reservationService.show(id);
    }

    //환자 : 예약 하기
    @Override
    @PostMapping("/patient/reservation")
    public Header create(@RequestBody Header<ReservationApiRequest> request) {
        return reservationService.create(request);
    }

    //환자 : 의사id,날짜 선택시 예약가능 시간 보여줌
    @PostMapping("/patient/{id}/reservation/getDate")
    //doctor id, string date
    public Header<ReservationDateApiResponse> showDate(@PathVariable String id, @RequestBody Header<ReservationDateApiRequest> request){
        return reservationService.showDate(id,request);
    }


    //의사 : 예약 홈화면에서 예약환자들 리스트를 보여줌
    @GetMapping("/doctor/{id}/reservation")
    public Header<List<ReservationDoctorDTO>> showPatientResList(@PathVariable String id){
        return reservationService.showPatientResList(id);
    }

    //의사 : 날짜로 예약상태 조회
    @PostMapping("/doctor/{id}/reservation/search")
    public Header<?> search(@PathVariable String id,@RequestBody Header<ReservationApiRequest> request){
        return reservationService.search(id,request);
    }

    @Override
    public Header<ReservationApiResponse> read(String id) {
        return null;
    }

    @Override
    public Header<ReservationApiResponse> update(Header<ReservationApiRequest> request) {
        return null;
    }

    @Override
    @DeleteMapping("/{num}/reservation/delete")
    public Header delete(@PathVariable String num) {
        log.info("delete reservation number: "+num);
        return reservationService.delete(num);
    }

}



