package com.example.pfa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.TimeSlot;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    @Query("select t from TimeSlot t join t.professors p where p.id = :id")
    List<TimeSlot> findTimeSlotByProfessor(@Param("id") Long id);
    // Example custom query method
    // List<TimeSlot> findBySomeCondition(String condition);
}
