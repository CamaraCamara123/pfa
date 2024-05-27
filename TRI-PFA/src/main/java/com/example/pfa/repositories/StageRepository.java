package com.example.pfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Stage;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    @Query("select s from Stage s where s.ingStudent.id =:id")
    List<Stage> getStagesByStudent(@Param("id")Long id);
}