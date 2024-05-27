package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.Salle;
import com.example.pfa.services.SalleService;

import java.util.List;

@RestController
@RequestMapping("/salles")
@CrossOrigin("*")
public class SalleController {
    @Autowired
    private SalleService salleService;


    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Salle> saveSalle(@RequestBody Salle salle) {
        return salleService.saveSalle(salle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salle> getSalleById(@PathVariable Long id) {
        return salleService.getSalleById(id);
    }

    @GetMapping
    public ResponseEntity<List<Salle>> getAllSalles() {
        return salleService.getAllSalles();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalleById(@PathVariable Long id) {
        return salleService.deleteSalleById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Salle> updateSalle(@PathVariable Long id, @RequestBody Salle updatedEntity) {
        return salleService.updateSalle(id, updatedEntity);
    }

    
}