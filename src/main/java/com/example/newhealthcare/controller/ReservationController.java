package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.network.request.ReservationApiRequest;
import com.example.newhealthcare.model.network.response.*;
import com.example.newhealthcare.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("")
public class ReservationController implements CrudInterface<ReservationApiRequest,ReservationApiResponse> {

    @Autowired
    private ReservationService reservationService;

    @Override
    @PostMapping("/patient/reservation")
    public Header<ReservationApiResponse> create(@RequestBody Header<ReservationApiRequest> request) {
        return reservationService.create(request);
    }
    //예약시 connect된 의사 리스트 보여줌
    @GetMapping("/patient/{id}/reservation")
    public Header<ReservationHomeApiResponse> show(@PathVariable String id){
        return reservationService.show(id);
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



