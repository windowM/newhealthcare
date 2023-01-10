package com.example.newhealthcare.repository;

import com.example.newhealthcare.model.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {

}
