package com.example.newhealthcare.repository;

import com.example.newhealthcare.model.entity.DandP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DandPRepository extends JpaRepository<DandP,Long> {
    DandP findByCode(String code);
}
