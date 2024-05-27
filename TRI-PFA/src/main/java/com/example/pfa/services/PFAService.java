package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.GroupeProject;
import com.example.pfa.entities.PFA;
import com.example.pfa.repositories.GroupeProjectRepository;
import com.example.pfa.repositories.PFARepository;

import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class PFAService {

    @Autowired
    private PFARepository pfaRepository;

    @Autowired
    private GroupeProjectRepository groupeProjectRepository;

    @Autowired
    private FileUploadImpl fileUploadImpl;

    // CRUD methods
    public ResponseEntity<PFA> savePFA(PFA pfa, Long id) {
        PFA savedEntity = pfaRepository.save(pfa);
        GroupeProject gp = groupeProjectRepository.getById(id);
        gp.setPfa(pfa);
        groupeProjectRepository.save(gp);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<PFA> getPFAById(Long id) {
        PFA foundEntity = pfaRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<PFA>> getAllPFAs() {
        List<PFA> entities = pfaRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deletePFAById(Long id) {
        PFA pfa = pfaRepository.getById(id);
        try {
            pfaRepository.deleteById(id);
            fileUploadImpl.deleteFile(pfa.getUrl());
        } catch (Exception e) {
            System.err.println("erreur de suppression : " + e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<PFA> updatePFA(Long id, PFA updatedEntity) throws Exception {
        if (!pfaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        PFA pfa = pfaRepository.findById(id).get();
        if (pfa.getUrl() != null && updatedEntity.getUrl() == null) {
            updatedEntity.setUrl(pfa.getUrl());
        } else if (pfa.getUrl() != null && updatedEntity.getUrl() != null) {
            fileUploadImpl.deleteFile(pfa.getUrl());
        }
        updatedEntity.setId(id);
        PFA result = pfaRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }
}