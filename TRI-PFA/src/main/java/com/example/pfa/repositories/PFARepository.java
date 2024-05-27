package com.example.pfa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.PFA;

@Repository
public interface PFARepository extends JpaRepository<PFA, Long> {
    // Example custom query method
    // List<PFA> findBySomeCondition(String condition);
}