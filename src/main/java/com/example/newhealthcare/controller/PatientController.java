package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.network.request.PatientApiRequest;
import com.example.newhealthcare.model.network.response.PatientApiResponse;
import com.example.newhealthcare.service.DandPService;
import com.example.newhealthcare.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/patient")
public class PatientController implements CrudInterface<PatientApiRequest, PatientApiResponse> {
    @Autowired
    private PatientService patientService;

    @Autowired
    private DandPService dandPService;

    //환자 로그인
    @PostMapping("/login")
    public Header login(@RequestBody Header<PatientApiRequest> request){
        log.info("patinet login: {}",request);
        return patientService.login(request);
    }

    //환자 회원가입
    @Override
    @PostMapping("/signup")
    public Header<PatientApiResponse> create(@RequestBody Header<PatientApiRequest> request) {
        log.info("{}",request);
        return patientService.create(request);
    }

    //환자 홈 화면
    @Override
    @GetMapping("{id}")
    public Header<PatientApiResponse> read(@PathVariable String id) {
        log.info("read id : {}",id);
        return patientService.read(id);
    }


    /*====================== 환자와 의사 코드 관련 ==================*/

    //환자 코드 입력

    @PostMapping("{id}/code")
    public Header inputCode(@PathVariable String id,@RequestBody Header<PatientApiRequest> request){
        return patientService.inputCode(id,request);
    }

    /* ============================ 환자 info =======================*/

    //환자 정보 수정
    @Override
    @PutMapping("{id}/info/update")
    public Header<PatientApiResponse> update(Header<PatientApiRequest> request) {
        return patientService.update(request);
    }

    //환자 회원 삭제
    @Override
    @DeleteMapping("{id}/info/delete")
    public Header<PatientApiResponse> delete(@PathVariable String id) {
        log.info("delete id: "+id);
        return patientService.delete(id);
    }

}
