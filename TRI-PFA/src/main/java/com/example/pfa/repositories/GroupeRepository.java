package com.example.pfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Groupe;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {

    @Query("select g from Groupe g where g.section.id = :id")
    List<Groupe> findGroupesBySection(@Param("id") Long id);
    // Example custom query method
    // List<Groupe> findBySomeCondition(String condition);

    @Query("select g from Groupe g join g.prepaStudents ps where ps.id = :id and g.type = :type")
    ResponseEntity<Groupe> findGroupeTdStudent(@Param("id") Long id, @Param("type") String type);

    @Query("select g from Groupe g join g.prepaStudents s where s.id = :id")
    List<Groupe> findPrepaStudentGroupes(@Param("id") Long id);
}