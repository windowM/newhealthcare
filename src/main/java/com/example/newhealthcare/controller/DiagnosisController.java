package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.DoctorDiagnosisResponseDTO;
import com.example.newhealthcare.dto.PatientResponseDTO;
import com.example.newhealthcare.model.network.request.DandPApiRequest;
import com.example.newhealthcare.model.network.request.doctor.DoctorDiagnosisApiRequest;
import com.example.newhealthcare.model.network.request.doctor.DoctorPreApiRequest;
import com.example.newhealthcare.model.network.response.doctor.DoctorDiagnosisApiResponse;
import com.example.newhealthcare.service.DiagnosisService;
import com.example.newhealthcare.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // Json 형태로 객체 데이터를 반환
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private DoctorService doctorService;

    //의사 : 진단 화면 (환자 목록)
    @GetMapping("/doctor/{id}/diagnosis")
    public  Header<List<PatientResponseDTO>> read(@PathVariable String id){
        return diagnosisService.read(id);
    }

    //의사 : 진단 화면 ( 환자 개인 내역)
    @PostMapping("/doctor/diagnosis/detail")
    public  Header<List<DoctorDiagnosisApiResponse>> detail(@RequestBody Header<DandPApiRequest> request){
        return diagnosisService.detail(request);
    }

    //의사 : 진단 추가
    @PostMapping("/doctor/diagnosis")
    public Header<DoctorDiagnosisApiResponse> diaCreate(@RequestBody Header<DoctorDiagnosisApiRequest> request){
        return diagnosisService.diaCreate(request);
    }

    //의사 : 처방 추가
    @PostMapping("/doctor/{diaNum}/prescription")
    public Header preCreate(@PathVariable Long diaNum, @RequestBody Header<DoctorPreApiRequest> request){
        return diagnosisService.preCreate(diaNum,request);
    }

}
