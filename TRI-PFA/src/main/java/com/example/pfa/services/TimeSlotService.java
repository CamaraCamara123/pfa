package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Professor;
import com.example.pfa.entities.TimeSlot;
import com.example.pfa.repositories.ProfessorRepository;
import com.example.pfa.repositories.TimeSlotRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    // CRUD methods
    public ResponseEntity<TimeSlot> saveTimeSlot(TimeSlot timeSlot) {
        TimeSlot savedEntity = timeSlotRepository.save(timeSlot);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<TimeSlot> getTimeSlotById(Long id) {
        TimeSlot foundEntity = timeSlotRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        List<TimeSlot> entities = timeSlotRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteTimeSlotById(Long id) {
        timeSlotRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<TimeSlot> updateTimeSlot(Long id, TimeSlot updatedEntity) {
        if (!timeSlotRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        TimeSlot result = timeSlotRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<TimeSlot>> getTimeSlotsByProfessor(Long id) {
        List<TimeSlot> entities = timeSlotRepository.findTimeSlotByProfessor(id);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<TimeSlot> changeTimeSlot(Long idTimeSlot, Long idProf) {
        TimeSlot t = timeSlotRepository.findById(idTimeSlot).get();
        Professor p = professorRepository.findById(idProf).get();
        t.getProfessors().add(p);
        return ResponseEntity.ok(timeSlotRepository.save(t));
    }

    public ResponseEntity<TimeSlot> removeFromTimeSlot(Long idTimeSlot, Long idProf) {
        TimeSlot t = timeSlotRepository.findById(idTimeSlot).get();
        Professor p = professorRepository.findById(idProf).get();
        t.getProfessors().remove
        (p);
        return ResponseEntity.ok(timeSlotRepository.save(t));
    }
}
