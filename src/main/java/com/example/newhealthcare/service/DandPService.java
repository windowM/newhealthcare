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


}
