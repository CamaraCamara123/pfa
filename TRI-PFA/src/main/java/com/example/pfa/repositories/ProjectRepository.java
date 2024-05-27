package com.example.pfa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Example custom query method
    // List<Project> findBySomeCondition(String condition);
}