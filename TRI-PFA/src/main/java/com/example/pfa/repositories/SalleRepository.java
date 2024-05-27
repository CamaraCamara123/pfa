package com.example.pfa.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Salle;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
    // Example custom query method
    // List<Salle> findBySomeCondition(String condition);
}