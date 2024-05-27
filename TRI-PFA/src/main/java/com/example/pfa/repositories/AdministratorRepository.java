package com.example.pfa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    // Example custom query method
    // List<Administrator> findBySomeCondition(String condition);
}