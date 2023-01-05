package com.example.newhealthcare.repository;

import com.example.newhealthcare.model.entity.DandP;
import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DandPRepository extends JpaRepository<DandP,Long> {
    Optional<DandP> findByCode(String code);
    Optional<DandP> findByDoctorId(Doctor id);
    Optional<DandP> findByPatientId(Patient id);

}
