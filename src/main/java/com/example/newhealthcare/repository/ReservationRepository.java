package com.example.newhealthcare.repository;

import com.example.newhealthcare.dto.ReservationInfoDTO;
import com.example.newhealthcare.model.entity.Patient;
import com.example.newhealthcare.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findBySelDoctorId(String doctorId);
    List<Reservation> findByPatientId(Patient patient);
    List<Reservation> findByResDateContaining(String resDate);

}
