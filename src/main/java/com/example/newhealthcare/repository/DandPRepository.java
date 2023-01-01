package com.example.newhealthcare.repository;

import com.example.newhealthcare.entity.DandP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DandPRepository extends JpaRepository<DandP,Integer> {

}
