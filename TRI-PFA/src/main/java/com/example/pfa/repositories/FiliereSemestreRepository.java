package com.example.pfa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.FiliereSemestre;

@Repository
public interface FiliereSemestreRepository extends JpaRepository<FiliereSemestre, Long> {

    @Query("select fs from FiliereSemestre fs where fs.semestre.id = :id")
    Optional<FiliereSemestre> findFiliereSemestreBySemestre(@Param("id") Long id);
    // Example custom query method
    // List<FiliereSemestre> findBySomeCondition(String condition);

    @Query("select fs from FiliereSemestre fs where fs.filiere.id = :id")
    Optional<FiliereSemestre> findFiliereSemestreByFiliere(Long id);
}