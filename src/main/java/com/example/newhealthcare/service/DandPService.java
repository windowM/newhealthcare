package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.model.entity.DandP;
import com.example.newhealthcare.model.entity.Patient;
import com.example.newhealthcare.model.network.response.DandPApiResponse;
import com.example.newhealthcare.repository.DandPRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DandPService {
    @Autowired
    private final DandPRepository dandPRepository;

    @Autowired
    private final DoctorRepository doctorRepository;

    @Autowired
    private final PatientRepository patientRepository;

    public Header create(Header<DandPApiResponse> response){
        DandPApiResponse dandPApiResponse=response.getData();
        Optional<DandP> dandP=dandPRepository.findByCode(dandPApiResponse.getCode());
        if(!dandP.isPresent()) {
            DandP dandP1=dandP.get();
            dandP1 = DandP.builder()
                    .code(dandPApiResponse.getCode())
                    .patientId(patientRepository.getReferenceById(dandPApiResponse.getPatientId()))
                    .doctorId(doctorRepository.getReferenceById(dandPApiResponse.getDoctorId()))
                    .build();

            DandP newDandP = dandPRepository.save(dandP1);
            return Header.OK();
        }
        else{
            return Header.ERROR("존재하는 코드번호");
        }
    }

    public Header<DandPApiResponse> response(DandP dandP){
        DandPApiResponse dandPApiResponse=DandPApiResponse.builder()
                .code(dandP.getCode())
                .patientId(dandP.getPatientId().getPatientId())
                .doctorId(dandP.getDoctorId().getDoctorId())
                .build();
        return Header.OK(dandPApiResponse);
    }

}
