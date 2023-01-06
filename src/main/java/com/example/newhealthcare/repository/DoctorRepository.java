package com.example.newhealthcare.repository;

import com.example.newhealthcare.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,String> {
//    Doctor findByIdAndUsable(String doctorId,Long usable);
//    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByCode(String code);
}
