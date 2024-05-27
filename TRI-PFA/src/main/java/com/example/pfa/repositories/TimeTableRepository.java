package com.example.pfa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pfa.entities.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    @Query("select t from TimeTable t where t.section = :id")
    Optional<TimeTable> findTimeTableBySection(@Param("id") Long id);
    // Example custom query method
    // List<TimeTable> findBySomeCondition(String condition);
}