package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Salle;
import com.example.pfa.repositories.SalleRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;

    // CRUD methods
    public ResponseEntity<Salle> saveSalle(Salle salle) {
        Salle savedEntity = salleRepository.save(salle);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Salle> getSalleById(Long id) {
        Salle foundEntity = salleRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Salle>> getAllSalles() {
        List<Salle> entities = salleRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteSalleById(Long id) {
        salleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Salle> updateSalle(Long id, Salle updatedEntity) {
        if (!salleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Salle result = salleRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }
}