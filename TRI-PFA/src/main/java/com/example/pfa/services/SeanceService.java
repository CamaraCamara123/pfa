package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Seance;
import com.example.pfa.repositories.SeanceRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class SeanceService {

    @Autowired
    private SeanceRepository seanceRepository;

    // CRUD methods
    public ResponseEntity<Seance> saveSeance(Seance seance) {
        Seance savedEntity = seanceRepository.save(seance);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Seance> getSeanceById(Long id) {
        Seance foundEntity = seanceRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Seance>> getAllSeances() {
        List<Seance> entities = seanceRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteSeanceById(Long id) {
        seanceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Seance> updateSeance(Long id, Seance updatedEntity) {
        if (!seanceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Seance result = seanceRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<Seance>> getSeancesByGroupe(Long id) {
        List<Seance> entities = seanceRepository.findSeancesByGroupe(id);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<List<Seance>> getSeancesByTimeTable(Long id) {
        List<Seance> entities = seanceRepository.findSeancesByTimeTable(id);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<List<Seance>> getSeancesByElement(Long id) {
        List<Seance> entities = seanceRepository.findSeancesByElement(id);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<List<Seance>> getSeancesBySalle(Long id) {
        List<Seance> entities = seanceRepository.findSeancesBySalle(id);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<List<Seance>> getSeancesByTimeSlot(Long id) {
        List<Seance> entities = seanceRepository.findSeancesByTimeSlot(id);
        return ResponseEntity.ok(entities);
    }
}
