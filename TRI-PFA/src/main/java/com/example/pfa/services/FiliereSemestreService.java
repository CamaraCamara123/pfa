package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.FiliereSemestre;
import com.example.pfa.repositories.FiliereSemestreRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class FiliereSemestreService {

    @Autowired
    private FiliereSemestreRepository filiereSemestreRepository;

    // CRUD methods
    public ResponseEntity<FiliereSemestre> saveFiliereSemestre(FiliereSemestre filiereSemestre) {
        FiliereSemestre savedEntity = filiereSemestreRepository.save(filiereSemestre);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<FiliereSemestre> getFiliereSemestreById(Long id) {
        FiliereSemestre foundEntity = filiereSemestreRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<FiliereSemestre>> getAllFiliereSemestres() {
        List<FiliereSemestre> entities = filiereSemestreRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteFiliereSemestreById(Long id) {
        filiereSemestreRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<FiliereSemestre> updateFiliereSemestre(Long id, FiliereSemestre updatedEntity) {
        if (!filiereSemestreRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        FiliereSemestre result = filiereSemestreRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<FiliereSemestre> getFiliereSemestreBySemestre(Long id) {
        FiliereSemestre foundEntity = filiereSemestreRepository.findFiliereSemestreBySemestre(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<FiliereSemestre> getFiliereSemestreByFiliere(Long id) {
        FiliereSemestre foundEntity = filiereSemestreRepository.findFiliereSemestreByFiliere(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }
}