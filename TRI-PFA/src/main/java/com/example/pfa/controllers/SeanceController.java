package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.Seance;
import com.example.pfa.services.SeanceService;

import java.util.List;

@RestController
@RequestMapping("/seances")
@CrossOrigin("*")
public class SeanceController {
    @Autowired
    private SeanceService seanceService;

    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Seance> saveSeance(@RequestBody Seance seance) {
        return seanceService.saveSeance(seance);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seance> getSeanceById(@PathVariable Long id) {
        return seanceService.getSeanceById(id);
    }

    @GetMapping
    public ResponseEntity<List<Seance>> getAllSeances() {
        return seanceService.getAllSeances();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeanceById(@PathVariable Long id) {
        return seanceService.deleteSeanceById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seance> updateSeance(@PathVariable Long id, @RequestBody Seance updatedEntity) {
        return seanceService.updateSeance(id, updatedEntity);
    }

    @GetMapping("/groupe/{id}")
    public ResponseEntity<List<Seance>> getSeancesBygroupe(@PathVariable Long id) {
        return seanceService.getSeancesByGroupe(id);
    }

    @GetMapping("/salle/{id}")
    public ResponseEntity<List<Seance>> getSeancesBySalle(@PathVariable Long id) {
        return seanceService.getSeancesBySalle(id);
    }

    @GetMapping("/element/{id}")
    public ResponseEntity<List<Seance>> getSeancesByElement(@PathVariable Long id) {
        return seanceService.getSeancesByElement(id);
    }

    @GetMapping("/timeSlot/{id}")
    public ResponseEntity<List<Seance>> getSeancesByTimeSlot(@PathVariable Long id) {
        return seanceService.getSeancesByTimeSlot(id);
    }

    @GetMapping("/timeTable/{id}")
    public ResponseEntity<List<Seance>> getSeancesByTimeTable(@PathVariable Long id) {
        return seanceService.getSeancesByTimeSlot(id);
    }
}
