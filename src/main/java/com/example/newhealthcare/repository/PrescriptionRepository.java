package com.example.newhealthcare.repository;

import com.example.newhealthcare.model.entity.Diagnosis;
import com.example.newhealthcare.model.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    List<Prescription> findByDiaNum(Diagnosis diaNum);
}
