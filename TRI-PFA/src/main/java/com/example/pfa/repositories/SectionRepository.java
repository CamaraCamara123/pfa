package com.example.pfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("select s from Section s where s.semestre.id = :id")
    List<Section> findSectionsBySemestre(@Param("id") Long id);
    // Example custom query method
    // List<Section> findBySomeCondition(String condition);
}