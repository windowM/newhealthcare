package com.example.newhealthcare.controller;

import com.example.newhealthcare.dto.HomeResponseDTO;
import com.example.newhealthcare.dto.patientdto.PatientResponseDTO;
import com.example.newhealthcare.dto.ResultDTO;
import com.example.newhealthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientService patientService;
    @Autowired
    public PatientController(PatientService patientService){
        this.patientService=patientService;
    }
    @GetMapping("/")
    public void home(){
        System.out.println("show home");
    }

    @PostMapping("/login")
    public ResultDTO login(@RequestBody PatientResponseDTO patientResponseDTO){
        return patientService.login(patientResponseDTO);
    }

    @PostMapping("/signup")
    public ResultDTO signup(@RequestBody PatientResponseDTO patientResponseDTO){
        return patientService.create(patientResponseDTO);
    }


    @GetMapping("/{id}")
    public HomeResponseDTO showUserHome(@PathVariable("id") String id){
        System.out.println("id:"+id+"님의 홈 화면");
        return patientService.home(id);
    }

    @GetMapping("{id}/code")
    public void showCode(@PathVariable("id") String id){
        System.out.println("id : "+id+"님의 코드 입력 화면");
    }

    @PostMapping("{id}/code")
    public ResultDTO conCode(@PathVariable("id") String id,@RequestParam String code){
        System.out.println("코드 매칭 검사중...");
        return patientService.connectCode(id,code);
    }
}
