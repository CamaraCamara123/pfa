package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Semestre;
import com.example.pfa.repositories.SemestreRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class SemestreService {

    @Autowired
    private SemestreRepository semestreRepository;

    // CRUD methods
    public ResponseEntity<Semestre> saveSemestre(Semestre semestre) {
        Semestre savedEntity = semestreRepository.save(semestre);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Semestre> getSemestreById(Integer id) {
        Semestre foundEntity = semestreRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Semestre>> getAllSemestres() {
        List<Semestre> entities = semestreRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteSemestreById(Integer id) {
        semestreRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Semestre> updateSemestre(Integer id, Semestre updatedEntity) {
        if (!semestreRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Semestre result = semestreRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }
}
