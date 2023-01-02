package com.example.newhealthcare.service;

import com.example.newhealthcare.dto.ResultDTO;
import com.example.newhealthcare.dto.dandpdto.DandPResponseDTO;
import com.example.newhealthcare.repository.DandPRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DandPService {
    @Autowired
    private final DandPRepository dandPRepository;

//    public ResultDTO connect(DandPResponseDTO dandPResponseDTO){
//        System.out.println("의사:"+dandPResponseDTO.getDoctorId()+
//                "와\n 환자 :"+dandPResponseDTO.getPatientId()+"\n 연결중...");
//
//
//
//    }

}
