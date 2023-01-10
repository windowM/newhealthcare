package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.model.network.request.doctor.DoctorDiagnosisApiRequest;
import com.example.newhealthcare.service.DiagnosisService;
import com.example.newhealthcare.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController // Json 형태로 객체 데이터를 반환
@RequestMapping("/{user}/{id}/diagnosis")   //doctor/aaaa/diagnosis
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private DoctorService doctorService;

    //진단 조회
    @GetMapping("")
    public  Header<?> read(@PathVariable String user, @PathVariable String id){
        return diagnosisService.read(user,id);
    }

    //진단 추가
    @PostMapping("")
    public Header create(@PathVariable String user,@PathVariable String id,@RequestBody Header<DoctorDiagnosisApiRequest> request){
        return diagnosisService.create(user,id,request);
    }
}
