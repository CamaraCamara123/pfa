package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Professor;
import com.example.pfa.repositories.ProfessorRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // CRUD methods
    public ResponseEntity<Professor> saveProfessor(Professor professor) {

        professor.setPassword(passwordEncoder.encode(professor.getPassword()));
        Professor savedEntity = professorRepository.save(professor);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Professor> getProfessorById(Long id) {
        Professor foundEntity = professorRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Professor>> getAllProfessors() {
        List<Professor> entities = professorRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteProfessorById(Long id) {
        professorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Professor> updateProfessor(Long id, Professor updatedEntity) {
        if (!professorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Professor result = professorRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }
}