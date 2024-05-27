package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.TimeSlot;
import com.example.pfa.services.TimeSlotService;

import java.util.List;


@RestController
@RequestMapping("/timeslots")
@CrossOrigin("*")
public class TimeSlotController {
    @Autowired
    private TimeSlotService timeSlotService;


    // CRUD endpoints
    @PostMapping
    public ResponseEntity<TimeSlot> saveTimeSlot(@RequestBody TimeSlot timeSlot) {
        return timeSlotService.saveTimeSlot(timeSlot);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlot> getTimeSlotById(@PathVariable Long id) {
        return timeSlotService.getTimeSlotById(id);
    }

    @GetMapping
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        return timeSlotService.getAllTimeSlots();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeSlotById(@PathVariable Long id) {
        return timeSlotService.deleteTimeSlotById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeSlot> updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlot updatedEntity) {
        return timeSlotService.updateTimeSlot(id, updatedEntity);
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<List<TimeSlot>> getTimeSlotByProfessor(@PathVariable Long id) {
        return timeSlotService.getTimeSlotsByProfessor(id);
    }

    @GetMapping("/changeTimeSlot/professor/{idTimeSlot}/{idProf}")
    public ResponseEntity<TimeSlot> changeTimeSlot(@PathVariable Long idTimeSlot, @PathVariable Long idProf) {
        return timeSlotService.changeTimeSlot(idTimeSlot,idProf);
    }

    @DeleteMapping("/removeProf_from_timeSlot/{idTimeSlot}/{idProf}")
    public ResponseEntity<TimeSlot> removeFromTimeSlot(@PathVariable Long idTimeSlot, @PathVariable Long idProf) {
        return timeSlotService.removeFromTimeSlot(idTimeSlot,idProf);
    }
}