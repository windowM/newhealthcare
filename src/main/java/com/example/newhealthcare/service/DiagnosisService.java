package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.DoctorResponseDTO;
import com.example.newhealthcare.model.entity.Diagnosis;
import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.network.request.DiagnosisApiRequest;
import com.example.newhealthcare.model.network.response.DiagnosisApiResponse;
import com.example.newhealthcare.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisService {
    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public Header create(Header<DiagnosisApiRequest> request){
        DiagnosisApiRequest diagnosisApiRequest=request.getData();
        Optional<Diagnosis> diagnosis=diagnosisRepository.findById(diagnosisApiRequest.getDiaNum());

        return diagnosis
                .map(diagnosis1 -> {
                    List<DoctorResponseDTO> doctorResponseDTOS=new ArrayList<DoctorResponseDTO>();
                    DiagnosisApiResponse diagnosisApiResponse=DiagnosisApiResponse.builder()
                            .diaNum(diagnosisApiRequest.getDiaNum())
                            .doctorId(doctorResponseDTOS)
                            //.selectPatientId()
                            .disease(diagnosisApiRequest.getDisease())
                            .contents(diagnosisApiRequest.getContents())
                            .diaDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                            .build();

                    return Header.OK();
                }).orElseGet(()->Header.ERROR("이미 있는 진단 번호"));
    }
}
