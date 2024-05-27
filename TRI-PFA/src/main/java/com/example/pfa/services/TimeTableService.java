package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.TimeTable;
import com.example.pfa.repositories.TimeTableRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class TimeTableService {

    @Autowired
    private TimeTableRepository timeTableRepository;

    // CRUD methods
    public ResponseEntity<TimeTable> saveTimeTable(TimeTable timeTable) {
        TimeTable savedEntity = timeTableRepository.save(timeTable);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<TimeTable> getTimeTableById(Long id) {
        TimeTable foundEntity = timeTableRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<TimeTable>> getAllTimeTables() {
        List<TimeTable> entities = timeTableRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteTimeTableById(Long id) {
        timeTableRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<TimeTable> updateTimeTable(Long id, TimeTable updatedEntity) {
        if (!timeTableRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        TimeTable result = timeTableRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<TimeTable> getTimeTableBySection(Long id) {
        TimeTable foundEntity = timeTableRepository.findTimeTableBySection(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }
    
}