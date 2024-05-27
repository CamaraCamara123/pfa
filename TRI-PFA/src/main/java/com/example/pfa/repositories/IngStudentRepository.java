package com.example.pfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.IngStudent;

@Repository
public interface IngStudentRepository extends JpaRepository<IngStudent, Long> {

    @Query("select i from IngStudent i where i.filiereSemestre.id = :id")
    List<IngStudent> findIngStudentsByFiliereSemestre(@Param("id") Long id);
    // Example custom query method
    // List<IngStudent> findBySomeCondition(String condition);
}