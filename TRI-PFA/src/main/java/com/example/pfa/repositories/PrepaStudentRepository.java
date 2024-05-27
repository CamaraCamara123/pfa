package com.example.pfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Groupe;
import com.example.pfa.entities.PrepaStudent;
import com.example.pfa.entities.Semestre;

@Repository
public interface PrepaStudentRepository extends JpaRepository<PrepaStudent, Long> {

    PrepaStudent findPrepaStudentByCin(String cin);
    // Example custom query method
    // List<PrepaStudent> findBySomeCondition(String condition);

    PrepaStudent findPrepaStudentByCne(String cne);

    List<PrepaStudent> findBySemestre(Semestre semestre);

}