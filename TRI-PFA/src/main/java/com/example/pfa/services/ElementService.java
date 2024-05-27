package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Element;
import com.example.pfa.entities.Professor;
import com.example.pfa.repositories.ElementRepository;
import com.example.pfa.repositories.ProfessorRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class ElementService {

    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    // CRUD methods
    public ResponseEntity<Element> saveElement(Element element) {
        Element savedEntity = elementRepository.save(element);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Element> getElementById(Long id) {
        Element foundEntity = elementRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Element>> getAllElements() {
        List<Element> entities = elementRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteElementById(Long id) {
        elementRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Element> updateElement(Long id, Element updatedEntity) {
        if (!elementRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Element result = elementRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<Element>> getElementsByProfessor(Long id) {
        List<Element> entities = elementRepository.findElementsByProfessor(id);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<List<Element>> getElementsByModule(Long id) {
        List<Element> entities = elementRepository.findElementsByModule(id);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Element> changeProfessorElement(Long idProf, Long idElement) {
        Professor prof = professorRepository.findById(idProf).get();
        Element elt = elementRepository.findById(idElement).get();
        elt.setProfessor(prof);
        return ResponseEntity.ok(elt);
    }

}