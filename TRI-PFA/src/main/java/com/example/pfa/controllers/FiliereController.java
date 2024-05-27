package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.Filiere;
import com.example.pfa.services.FiliereService;

import java.util.List;

@RestController
@RequestMapping("/filieres")
@CrossOrigin("*")
public class FiliereController {
    @Autowired
    private FiliereService filiereService;


    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Filiere> saveFiliere(@RequestBody Filiere filiere) {
        return filiereService.saveFiliere(filiere);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filiere> getFiliereById(@PathVariable Long id) {
        return filiereService.getFiliereById(id);
    }

    @GetMapping
    public ResponseEntity<List<Filiere>> getAllFilieres() {
        return filiereService.getAllFilieres();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFiliereById(@PathVariable Long id) {
        return filiereService.deleteFiliereById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filiere> updateFiliere(@PathVariable Long id, @RequestBody Filiere updatedEntity) {
        return filiereService.updateFiliere(id, updatedEntity);
    }
}
