package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Groupe;
import com.example.pfa.repositories.GroupeRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class GroupeService {

    @Autowired
    private GroupeRepository groupeRepository;

    // CRUD methods
    public ResponseEntity<Groupe> saveGroupe(Groupe groupe) {
        Groupe savedEntity = groupeRepository.save(groupe);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Groupe> getGroupeById(Long id) {
        Groupe foundEntity = groupeRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Groupe>> getAllGroupes() {
        List<Groupe> entities = groupeRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteGroupeById(Long id) {
        groupeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Groupe> updateGroupe(Long id, Groupe updatedEntity) {
        if (!groupeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Groupe result = groupeRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<Groupe>> getGroupesBySection(Long id) {
        List<Groupe> entities = groupeRepository.findGroupesBySection(id);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Groupe> getGroupeTdByStudent(Long id,String type) {
        return groupeRepository.findGroupeTdStudent(id,type);
    }

    public ResponseEntity<List<Groupe>> getPrepaStudentGroupes(Long id){
        List<Groupe> groupes = groupeRepository.findPrepaStudentGroupes(id);
        return ResponseEntity.ok(groupes);
    }

}