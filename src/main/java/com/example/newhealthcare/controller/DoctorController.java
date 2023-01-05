package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.doctordto.DoctorResponseDTO;
import com.example.newhealthcare.dto.ResultDTO;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.network.request.DoctorApiRequest;
import com.example.newhealthcare.model.network.response.DoctorApiResponse;
import com.example.newhealthcare.model.network.response.DoctorHomeApiResponse;
import com.example.newhealthcare.service.DoctorService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@RestController // Json 형태로 객체 데이터를 반환
@RequestMapping("/doctor")
public class DoctorController implements CrudInterface<DoctorApiRequest,DoctorApiResponse> {
    @Autowired
    private DoctorService doctorService;

    //의사 로그인
    @PostMapping("/login")
    public Header login(@RequestBody Header<DoctorApiRequest> request){
        log.info("{}",request);
        return doctorService.login(request);
    }

    //의사 회원가입
    @Override
    @PostMapping("/signup")
    public Header<DoctorApiResponse> create(@RequestBody Header<DoctorApiRequest> request) {
        log.info("{}",request);
        return  doctorService.create(request);
    }

    //로그인 성공시 {id} 의사정보 넘기기
    @Override
    @GetMapping("/{id}")
    public Header<DoctorApiResponse> read(@PathVariable String id) {
        log.info("read id : {}",id);
        return doctorService.read(id);
    }

    //!!!!!!!!!!!!!!!!!!!!!수정 필요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //의사 정보 수정
    @Override
    @PutMapping("{id}/info/update")
    public Header<DoctorApiResponse> update(@RequestBody Header<DoctorApiRequest> doctorApiRequest) {
        return doctorService.update(doctorApiRequest);
    }

    //코드 발급
    @PutMapping("{id}/code")
    public Header updateCode(@PathVariable String id,@RequestBody Header<DoctorApiRequest> doctorApiRequest){
        return doctorService.updateCode(id,doctorApiRequest);
    }

    //의사 회원 삭제
    @Override
    @DeleteMapping("/{id}/info/delete")
    public Header delete(@PathVariable("id") String id) {
        log.info("delete id: "+id);
        return doctorService.delete(id);
    }

    //    @PostMapping("/login")
//    public DoctorResponseDTO ckLogin(@RequestBody DoctorResponseDTO doctorResponseDTO){
//        doctorResponseDTO = doctorService.login(doctorResponseDTO);
//        return doctorResponseDTO;
//    }
//
//    @PostMapping("/signup")
//    public ResultDTO signup(@Valid @RequestBody DoctorResponseDTO doctorResponseDTO){
//        return doctorService.create(doctorResponseDTO);
//    }
}
