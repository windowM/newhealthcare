package com.example.newhealthcare.repository;

import com.example.newhealthcare.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,String> {
    //Patient findByName(String name);

}
