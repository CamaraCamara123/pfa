package com.example.pfa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Semestre;

@Repository
public interface SemestreRepository extends JpaRepository<Semestre, Integer> {
    // Example custom query method
    // List<Semestre> findBySomeCondition(String condition);
}
