package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Section;
import com.example.pfa.repositories.SectionRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    // CRUD methods
    public ResponseEntity<Section> saveSection(Section section) {
        Section savedEntity = sectionRepository.save(section);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Section> getSectionById(Long id) {
        Section foundEntity = sectionRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Section>> getAllSections() {
        List<Section> entities = sectionRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteSectionById(Long id) {
        sectionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Section> updateSection(Long id, Section updatedEntity) {
        if (!sectionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Section result = sectionRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<Section>> getSectionsBySemestre(Long id) {
        List<Section> entities = sectionRepository.findSectionsBySemestre(id);
        return ResponseEntity.ok(entities);
    }
}
