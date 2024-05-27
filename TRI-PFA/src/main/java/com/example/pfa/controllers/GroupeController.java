package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.Groupe;
import com.example.pfa.services.GroupeService;

import java.util.List;

@RestController
@RequestMapping("/groupes")
@CrossOrigin("*")
public class GroupeController {
    @Autowired
    private GroupeService groupeService;

    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Groupe> saveGroupe(@RequestBody Groupe groupe) {
        return groupeService.saveGroupe(groupe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Groupe> getGroupeById(@PathVariable Long id) {
        return groupeService.getGroupeById(id);
    }

    @GetMapping
    public ResponseEntity<List<Groupe>> getAllGroupes() {
        return groupeService.getAllGroupes();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupeById(@PathVariable Long id) {
        return groupeService.deleteGroupeById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Groupe> updateGroupe(@PathVariable Long id, @RequestBody Groupe updatedEntity) {
        return groupeService.updateGroupe(id, updatedEntity);
    }

    @GetMapping("/section/{id}")
    public ResponseEntity<List<Groupe>> getGroupeBySection(@PathVariable Long id) {
        return groupeService.getGroupesBySection(id);
    }

    @GetMapping("/td_tp/student/{id}")
    public ResponseEntity<Groupe> getGroupeTdOrTpByStudent(@PathVariable Long id, @PathVariable String type) {
        return groupeService.getGroupeTdByStudent(id, type);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<Groupe>> getGroupesByStudent(@PathVariable Long id, @PathVariable String type) {
        return groupeService.getPrepaStudentGroupes(id);
    }
}