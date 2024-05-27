package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.Section;
import com.example.pfa.services.SectionService;

import java.util.List;

@RestController
@RequestMapping("/sections")
@CrossOrigin("*")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Section> saveSection(@RequestBody Section section) {
        return sectionService.saveSection(section);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable Long id) {
        return sectionService.getSectionById(id);
    }

    @GetMapping
    public ResponseEntity<List<Section>> getAllSections() {
        return sectionService.getAllSections();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSectionById(@PathVariable Long id) {
        return sectionService.deleteSectionById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable Long id, @RequestBody Section updatedEntity) {
        return sectionService.updateSection(id, updatedEntity);
    }

    @GetMapping("/semestre/{id}")
    public ResponseEntity<List<Section>> getSectionBySemestre(@PathVariable Long id) {
        return sectionService.getSectionsBySemestre(id);
    }
}