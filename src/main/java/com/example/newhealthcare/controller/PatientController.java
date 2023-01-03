package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.HomeResponseDTO;
import com.example.newhealthcare.dto.patientdto.PatientResponseDTO;
import com.example.newhealthcare.dto.ResultDTO;
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

    @Override
    @PostMapping("/signup")
    public Header<PatientApiResponse> create(@RequestBody Header<PatientApiRequest> request) {
        log.info("{}",request);
        return patientService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<PatientApiResponse> read(@PathVariable String id) {
        log.info("read id : {}",id);
        return patientService.read(id);
    }

    @Override
    @PutMapping("{id}/info/update")
    public Header<PatientApiResponse> update(Header<PatientApiRequest> request) {
        return patientService.update(request);
    }

    @Override
    @DeleteMapping("{id}/info/delete")
    public Header<PatientApiResponse> delete(@PathVariable String id) {
        log.info("delete id: "+id);
        return patientService.delete(id);
    }

//    public PatientController(PatientService patientService){
//        this.patientService=patientService;
//    }
//    @GetMapping("/")
//    public void home(){
//        patientService.read();
//        System.out.println("show home");
//    }
//
//    @PostMapping("/login")
//    public ResultDTO login(@RequestBody PatientResponseDTO patientResponseDTO){
//        return patientService.login(patientResponseDTO);
//    }
//
//    @PostMapping("/signup")
//    public ResultDTO signup(@RequestBody PatientResponseDTO patientResponseDTO){
//        return patientService.create(patientResponseDTO);
//    }
//
//
//
//    @GetMapping("/{id}")
//    public HomeResponseDTO showUserHome(@PathVariable("id") String id){
//        System.out.println("id:"+id+"님의 홈 화면");
//        return patientService.home(id);
//    }
//
//    @GetMapping("{id}/code")
//    public void showCode(@PathVariable("id") String id){
//        System.out.println("id : "+id+"님의 코드 입력 화면");
//    }
//
//    @PostMapping("{id}/code")
//    public ResultDTO conCode(@PathVariable("id") String id,@RequestParam("code") String code){
//        System.out.println("코드 매칭 검사중...");
//        return patientService.connectCode(id,code);
//    }
//
//    @DeleteMapping("{id}/info/delete")
//    public void delete(@PathVariable("id") String id){
//
//    }
}
