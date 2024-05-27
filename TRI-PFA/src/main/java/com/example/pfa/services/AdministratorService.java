package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Administrator;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class AdministratorService {

    @Autowired
    private com.example.pfa.repositories.AdministratorRepository AdministratorRepository;

    // CRUD methods
    public ResponseEntity<Administrator> saveAdministrator(Administrator Administrator) {
        Administrator savedEntity = AdministratorRepository.save(Administrator);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Administrator> getAdministratorById(Long id) {
        Administrator foundEntity = AdministratorRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Administrator>> getAllAdministrators() {
        List<Administrator> entities = AdministratorRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteAdministratorById(Long id) {
        AdministratorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Administrator> updateAdministrator(Long id, Administrator updatedEntity) {
        if (!AdministratorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Administrator result = AdministratorRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }
}