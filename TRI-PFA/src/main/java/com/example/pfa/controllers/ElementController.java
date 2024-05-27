package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.Element;
import com.example.pfa.services.ElementService;

import java.util.List;

@RestController
@RequestMapping("/elements")
@CrossOrigin("*")
public class ElementController {
    @Autowired
    private ElementService elementService;

    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Element> saveElement(@RequestBody Element element) {
        return elementService.saveElement(element);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Element> getElementById(@PathVariable Long id) {
        return elementService.getElementById(id);
    }

    @GetMapping
    public ResponseEntity<List<Element>> getAllElements() {
        return elementService.getAllElements();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElementById(@PathVariable Long id) {
        return elementService.deleteElementById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Element> updateElement(@PathVariable Long id, @RequestBody Element updatedEntity) {
        return elementService.updateElement(id, updatedEntity);
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<List<Element>> getElementsByProfessor(@PathVariable Long id) {
        return elementService.getElementsByProfessor(id);
    }

    @GetMapping("/module/{id}")
    public ResponseEntity<List<Element>> getElementsByModule(@PathVariable Long id) {
        return elementService.getElementsByModule(id);
    }

    @PostMapping("/professor/{id}")
    public ResponseEntity<Element> changeProfessorElement(@PathVariable Long idProf, @PathVariable Long idElement) {
        return elementService.changeProfessorElement(idProf,idElement);
    }
}
