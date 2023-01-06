package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.model.network.request.DiagnosisApiRequest;
import com.example.newhealthcare.model.network.response.DiagnosisApiResponse;
import com.example.newhealthcare.repository.DiagnosisRepository;
import com.example.newhealthcare.service.DiagnosisService;
import com.example.newhealthcare.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController // Json 형태로 객체 데이터를 반환
@RequestMapping("/doctor/diagnosis")
public class DiagnosisController {
    @Autowired
    private DiagnosisService diagnosisService;

    @PostMapping("/")
    public Header create(@RequestBody Header<DiagnosisApiRequest> request){
        return diagnosisService.create(request);
    }

}
