package com.example.newhealthcare.repository;

import com.example.newhealthcare.model.entity.Diagnosis;
import com.example.newhealthcare.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis,Long> {
    Optional<Doctor> findByDoctorId(String id);
}
