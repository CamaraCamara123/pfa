package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.Professor;
import com.example.pfa.services.ProfessorService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/professors")
@CrossOrigin("*")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;


    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Professor> saveProfessor(@RequestBody Professor professor, @RequestParam("createdAt") LocalDate createdAt) {
        System.out.println(createdAt);
        professor.setCreatedAt(createdAt);
        return professorService.saveProfessor(professor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id) {
        return professorService.getProfessorById(id);
    }

    @GetMapping
    public ResponseEntity<List<Professor>> getAllProfessors() {
        return professorService.getAllProfessors();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessorById(@PathVariable Long id) {
        return professorService.deleteProfessorById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(@PathVariable Long id, @RequestBody Professor updatedEntity) {
        return professorService.updateProfessor(id, updatedEntity);
    }
}