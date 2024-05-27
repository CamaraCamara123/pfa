package com.example.pfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.PFE;

@Repository
public interface PFERepository extends JpaRepository<PFE,Long> {

    @Query("select p from PFE p where p.encadrantAcademique.id = :id")
    List<PFE> getEncadrantPFEs(@Param("id") Long id);
    // Example custom query method
    // List<PFE> findBySomeCondition(String condition);
}