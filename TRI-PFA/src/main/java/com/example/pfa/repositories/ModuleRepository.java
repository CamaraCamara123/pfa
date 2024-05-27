package com.example.pfa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<com.example.pfa.entities.Module, Long> {
    // Example custom query method
    // List<Module> findBySomeCondition(String condition);
}