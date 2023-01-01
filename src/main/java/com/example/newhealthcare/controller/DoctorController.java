package com.example.newhealthcare.controller;

import com.example.newhealthcare.dto.doctordto.DoctorResponseDTO;
import com.example.newhealthcare.dto.ResultDTO;
import com.example.newhealthcare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController // Json 형태로 객체 데이터를 반환
@RequestMapping("/doctor")
public class DoctorController {

    private DoctorService doctorService;
    @Autowired
    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

//    @GetMapping("/log")//localhost:8080/log?id=1234&password=abcd    ->password는 파라미터 password와 같아야 매핑 가능
//    public String cklogin(@RequestParam String id,@RequestParam String password){
//        System.out.println("id"+id);
//        System.out.println("pw"+password);
//    }

    @PostMapping("/login")
    public DoctorResponseDTO ckLogin(@RequestBody DoctorResponseDTO doctorResponseDTO){
        doctorResponseDTO = doctorService.login(doctorResponseDTO);
        return doctorResponseDTO;
    }

    @PostMapping("/signup")
    public ResultDTO signup(@Valid @RequestBody DoctorResponseDTO doctorResponseDTO){
        return doctorService.create(doctorResponseDTO);
    }

//    @GetMapping("/doctor")
//    public String doc() {
//        return "doctor_list";
//    }

//    @GetMapping("/doctor/list")
//    public String list(Model model) {
//        List<Doctor> doctorList = this.doctorService.getList();
//        model.addAttribute("dl",doctorList );
//        return "doctor_list";
//    }

}
