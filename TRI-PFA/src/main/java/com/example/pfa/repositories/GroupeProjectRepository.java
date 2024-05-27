package com.example.pfa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.GroupeProject;

@Repository
public interface GroupeProjectRepository extends JpaRepository<GroupeProject, Long> {

    @Query("select gp from GroupeProject gp where gp.encadrant.id = :id")
    List<GroupeProject> findGroupeProjectsByProfessor(@Param("id") Long id);
    // Example custom query method
    // List<GroupeProject> findBySomeCondition(String condition);

    @Query("select gp from GroupeProject gp join gp.students s where s.id = :id")
    Optional<GroupeProject> findGroupeProjectByIngStudent(@Param("id") Long id);

    @Query("select gp from GroupeProject gp where gp.pfa.id = :id")
    Optional<GroupeProject> findGroupeProjectByPFA(@Param("id") Long id);

    @Query("select gp from GroupeProject gp where gp.filiereSemestre.id = :id")
    List<GroupeProject> findGroupeProjectsByFiliereSemestre(@Param("id") Long id);
}