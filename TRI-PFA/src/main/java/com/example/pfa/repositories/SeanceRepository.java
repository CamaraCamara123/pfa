package com.example.pfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.Seance;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

    @Query("select s from Seance s join s.groupes g where g.id = :id")
    List<Seance> findSeancesByGroupe(@Param("id") Long id);
    // Example custom query method
    // List<Seance> findBySomeCondition(String condition);

    @Query("select s from Seance s where s.timeTable.id = :id")
    List<Seance> findSeancesByTimeTable(@Param("id") Long id);

    @Query("select s from Seance s where s.element.id = :id")
    List<Seance> findSeancesByElement(@Param("id") Long id);

    @Query("select s from Seance s where s.salle.id = :id")
    List<Seance> findSeancesBySalle(@Param("id") Long id);

    @Query("select s from Seance s where s.timeSlot.id = :id")
    List<Seance> findSeancesByTimeSlot(@Param("id") Long id);
}
