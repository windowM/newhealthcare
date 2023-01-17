package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.network.request.reservation.ReservationApiRequest;
import com.example.newhealthcare.model.network.request.ReservationDateApiRequest;
import com.example.newhealthcare.model.network.response.reservation.ReservationApiResponse;
import com.example.newhealthcare.model.network.response.reservation.ReservationDateApiResponse;
import com.example.newhealthcare.model.network.response.reservation.ReservationHomePApiResponse;
import com.example.newhealthcare.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("")
public class ReservationController implements CrudInterface<ReservationApiRequest, ReservationApiResponse> {

    @Autowired
    private ReservationService reservationService;

    @Override
    @PostMapping("/patient/reservation")
    public Header<ReservationApiResponse> create(@RequestBody Header<ReservationApiRequest> request) {
        return reservationService.create(request);
    }

    //환자 : 예약시 connect된 의사 리스트 보여줌
    @GetMapping("/patient/{id}/reservation")
    public Header<ReservationHomePApiResponse> showDocList(@PathVariable String id){
        return reservationService.show(id);
    }

    //환자 : 의사id,날짜 선택시 예약가능 시간 보여줌
    @PostMapping("/patient/{id}/reservation/getDate")
    //doctor id, string date
    public Header<ReservationDateApiResponse> showDate(@PathVariable String id, @RequestBody Header<ReservationDateApiRequest> request){
        return reservationService.showDate(id,request);
    }

    @GetMapping("/doctor/{id}/reservation")
    public Header<?> showPatientResList(@PathVariable String id){
        return reservationService.showPatientResList(id);
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
    public Header delete(String id) {
        return null;
    }

}



