package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.pfa.entities.GroupeProject;
import com.example.pfa.entities.IngStudent;
import com.example.pfa.entities.PFA;
import com.example.pfa.entities.PFE;
import com.example.pfa.entities.Professor;
import com.example.pfa.repositories.IngStudentRepository;
import com.example.pfa.repositories.PFERepository;
import com.example.pfa.repositories.ProfessorRepository;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PFEService {

    @Autowired
    private PFERepository pfeRepository;

    @Autowired
    private IngStudentRepository ingStudentRepository;

    @Autowired
    private FileUploadImpl fileUploadImpl;

    @Autowired
    private ProfessorRepository professorRepository;

    // CRUD methods
    public ResponseEntity<PFE> savePFE(PFE pfe, Long id) {
        IngStudent st = ingStudentRepository.findById(id).get();
        PFE savedEntity = pfeRepository.save(pfe);
        st.setPfe(savedEntity);
        ingStudentRepository.save(st);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<PFE> getPFEById(Long id) {
        PFE foundEntity = pfeRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<PFE>> getAllPFEs() {
        List<PFE> entities = pfeRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deletePFEById(Long id) {
        PFE pfe = pfeRepository.getById(id);

        try {
            pfeRepository.deleteById(id);
            fileUploadImpl.deleteFile(pfe.getUrlRapport());
        } catch (Exception e) {
            System.err.println("erreur de suppression : " + e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<PFE> updatePFE(Long id, PFE updatedEntity) throws Exception {
        if (!pfeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        PFE pfe = pfeRepository.findById(id).get();
        if (pfe.getUrlRapport() != null) {
            updatedEntity.setUrlRapport(pfe.getUrlRapport());
        } 
        if (pfe.getEncadrantAcademique() != null) {
            updatedEntity.setEncadrantAcademique(pfe.getEncadrantAcademique());
        }
        updatedEntity.setId(id);
        PFE result = pfeRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<PFE>> getEncadrantPFEs(Long id) {
        return ResponseEntity.ok(pfeRepository.getEncadrantPFEs(id));
    }

    public ResponseEntity<List<PFE>> affectations() {
        List<PFE> pfes = pfeRepository.findAll();
        List<Professor> profs = professorRepository.findAll();
        List<Professor> profs2 = new ArrayList<>();
        List<Professor> profs3 = new ArrayList<>();
        Collections.shuffle(profs);
        for (PFE pfe : pfes) {
            if (!profs.isEmpty()) {
                Professor profAssigned = profs.remove(0);
                pfe.setEncadrantAcademique(profAssigned);
                profs2.add(profAssigned);
            } else if (profs.isEmpty() && !profs2.isEmpty()) {
                Professor profAssigned = profs2.remove(0);
                pfe.setEncadrantAcademique(profAssigned);
                profs3.add(profAssigned);
            } else if (profs.isEmpty() && profs2.isEmpty() && !profs3.isEmpty()) {
                Professor profAssigned = profs3.remove(0);
                pfe.setEncadrantAcademique(profAssigned);
                profs3.add(profAssigned);
            }
            pfeRepository.save(pfe);
        }

        List<PFE> pfeList = pfeRepository.findAll();
        return ResponseEntity.ok(pfeList);
    }

    public ResponseEntity<PFE> changeEncadrant(Long pfe, Long encadrant) {
        Professor p = professorRepository.findById(encadrant).get();
        PFE pfeEntity = pfeRepository.findById(pfe).get();
        pfeEntity.setEncadrantAcademique(p);
        pfeEntity = pfeRepository.save(pfeEntity);
        return ResponseEntity.ok(pfeEntity);

    }

    @SuppressWarnings("unlikely-arg-type")
    public ResponseEntity<PFE> upload(Long id, MultipartFile rapport) throws Exception {
        String fileUrl = fileUploadImpl.uploadFile(rapport);
        System.out.println("la cle ::::" + fileUrl);
        PFE pfe = pfeRepository.findById(id).get();
        if (pfe.getUrlRapport() != null && !pfe.equals("")) {
            fileUploadImpl.deleteFile(pfe.getUrlRapport().toString());
            System.out.println("url : " + fileUrl);
        }
        pfe.setUrlRapport(fileUrl);
        pfe = pfeRepository.save(pfe);
        return ResponseEntity.ok(null);
    }
}