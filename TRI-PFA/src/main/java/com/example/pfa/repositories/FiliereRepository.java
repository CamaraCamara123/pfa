package com.example.pfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Filiere;
import com.example.pfa.entities.FiliereSemestre;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    // Example custom query method
    // List<Filiere> findBySomeCondition(String condition);

    @Query("select fs from FiliereSemestre fs where fs.filiere.id = :id")
    List<FiliereSemestre> fsByFiliere(@Param("id") Long id);
}