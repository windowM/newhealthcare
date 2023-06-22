package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.model.network.request.sensor.SensorApiRequest;
import com.example.newhealthcare.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping("/add")
    public Header create(@RequestBody Header<SensorApiRequest> request) throws IOException {
        System.out.println(request.getData());
        return sensorService.create(request);
    }

}