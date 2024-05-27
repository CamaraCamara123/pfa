package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Element;
import com.example.pfa.entities.Module;

import com.example.pfa.repositories.ModuleRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    // CRUD methods
    public ResponseEntity<Module> saveModule(Module module) {
        Module savedEntity = moduleRepository.save(module);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Module> getModuleById(Long id) {
        Module foundEntity = moduleRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Module>> getAllModules() {
        List<Module> entities = moduleRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteModuleById(Long id) {
        moduleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Module> updateModule(Long id, Module updatedEntity) {
        if (!moduleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Module result = moduleRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Module> addElement(Long id, Element element) {
        Module module = moduleRepository.findById(id).get();
        element.setModule(module);
        return ResponseEntity.ok(module);
    }
}