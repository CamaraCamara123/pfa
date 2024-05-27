package com.example.pfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Element;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {

    @Query("select e from Element e where e.professor.id = :id")
    List<Element> findElementsByProfessor(@Param("id") Long id);
    // Example custom query method
    // List<Element> findBySomeCondition(String condition);

    @Query("select e from Element e where e.module.id = :id")
    List<Element> findElementsByModule(@Param("id") Long id);
}