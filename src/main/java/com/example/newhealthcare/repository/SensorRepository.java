package com.example.newhealthcare.repository;

import com.example.newhealthcare.entity.Doctor;
import com.example.newhealthcare.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor,String> {
}
