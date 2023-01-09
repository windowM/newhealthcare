package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.DoctorResponseDTO;
import com.example.newhealthcare.model.entity.Diagnosis;
import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.entity.Patient;
import com.example.newhealthcare.model.network.request.DiagnosisApiRequest;
import com.example.newhealthcare.model.network.response.DiagnosisApiResponse;
import com.example.newhealthcare.repository.DiagnosisRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.PatientRepository;
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
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

//    public Header<DiagnosisApiResponse> read(String id){
//        Optional<Doctor> diaDoctor = doctorRepository.findById(id);
//        return diaDoctor.map(doctor -> {})
//    }


    // 지금은 연결되지 않은 환자도 진단 가능하게 되어 있음...
    public Header create(String id,Header<DiagnosisApiRequest> request) {
        DiagnosisApiRequest diagnosisApiRequest = request.getData();
        Optional<Doctor> diaDoctor = doctorRepository.findById(id);
        Optional<Patient> selPatient = patientRepository.findById(diagnosisApiRequest.getSelectPatientId());
        if (diaDoctor.isPresent() && selPatient.isPresent()) {
            Doctor doctor1 = diaDoctor.get();

            Diagnosis diagnosis1 = Diagnosis.builder()
                    .doctorId(doctor1)
                    .selPatientId(diagnosisApiRequest.getSelectPatientId())
                    .disease(diagnosisApiRequest.getDisease())
                    .contents(diagnosisApiRequest.getContents())
                    .diaDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                    .build();

            diagnosisRepository.save(diagnosis1);

            return Header.OK();
        } else {
            return Header.ERROR("의사/환자 정보가 없다.");
        }
    }

}
