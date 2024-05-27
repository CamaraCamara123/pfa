package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.Semestre;
import com.example.pfa.services.SemestreService;

import java.util.List;

@RestController
@RequestMapping("/semestres")
@CrossOrigin("*")
public class SemestreController {
    @Autowired
    private SemestreService semestreService;


    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Semestre> saveSemestre(@RequestBody Semestre semestre) {
        return semestreService.saveSemestre(semestre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Semestre> getSemestreById(@PathVariable Integer id) {
        return semestreService.getSemestreById(id);
    }

    @GetMapping
    public ResponseEntity<List<Semestre>> getAllSemestres() {
        return semestreService.getAllSemestres();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemestreById(@PathVariable Integer id) {
        return semestreService.deleteSemestreById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Semestre> updateSemestre(@PathVariable Integer id, @RequestBody Semestre updatedEntity) {
        return semestreService.updateSemestre(id, updatedEntity);
    }
}
