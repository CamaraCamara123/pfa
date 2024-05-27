package com.example.pfa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    // Example custom query method
    // List<Professor> findBySomeCondition(String condition);
}