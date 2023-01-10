package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.model.entity.Diagnosis;
import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.entity.Prescription;
import com.example.newhealthcare.model.network.request.doctor.DoctorApiRequest;
import com.example.newhealthcare.model.network.request.doctor.DoctorDiagnosisApiRequest;
import com.example.newhealthcare.model.network.request.doctor.DoctorPreApiRequest;
import com.example.newhealthcare.model.network.response.doctor.DoctorPreApiResponse;
import com.example.newhealthcare.repository.DiagnosisRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PrescriptionService {
    @Autowired
    final PrescriptionRepository prescriptionRepository;

    @Autowired
    final DoctorRepository doctorRepository;

    @Autowired
    final DiagnosisRepository diagnosisRepository;


    public Header<DoctorPreApiResponse> create(String user,String id,Header<DoctorPreApiRequest> request){
        if(user.equals("doctor")){
            DoctorPreApiRequest doctorPreApiRequest=request.getData();
            Optional<Doctor> doctor=doctorRepository.findById(id);
            if(doctor.isPresent()){
                Optional<Diagnosis> diagnosis=diagnosisRepository.findById(doctorPreApiRequest.getDiaNum());
                if(diagnosis.isPresent()){
                    Prescription prescription=Prescription.builder()
                            .diaNum(diagnosis.get())
                            .preName(doctorPreApiRequest.getPreName())
                            .contents(doctorPreApiRequest.getContents())
                            .oneDay(doctorPreApiRequest.getOneDay())
                            .total(doctorPreApiRequest.getTotal())
                            .preDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                            .build();
                    prescriptionRepository.save(prescription);

                    DoctorPreApiResponse doctorPreApiResponse= DoctorPreApiResponse.builder()
                            .preNum(prescription.getPreNum())
                            .preName(prescription.getPreName())
                            .contents(prescription.getContents())
                            .diaNum(prescription.getPreNum())
                            .oneDay(prescription.getOneDay())
                            .total(prescription.getTotal())
                            .preDate(prescription.getPreDate())
                            .build();
                    return Header.OK(doctorPreApiResponse);
                }
            }else{
                return Header.ERROR("의사 정보 없음");
            }

        }else if(user.equals("patient")){

        }
        return Header.OK();
    }
}
