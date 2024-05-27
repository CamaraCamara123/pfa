package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.GroupeProject;
import com.example.pfa.entities.PrepaStudent;
import com.example.pfa.entities.Semestre;
import com.example.pfa.services.PrepaStudentService;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/prepaStudents")
@CrossOrigin("*")
public class PrepaStudentController {
    @Autowired
    private PrepaStudentService prepaStudentService;
    


    // CRUD endpoints
    @PostMapping
    public ResponseEntity<PrepaStudent> savePrepaStudent(@RequestBody PrepaStudent prepaStudent) {
        return prepaStudentService.savePrepaStudent(prepaStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrepaStudent> getPrepaStudentById(@PathVariable Long id) {
        return prepaStudentService.getPrepaStudentById(id);
    }

    @GetMapping
    public ResponseEntity<List<PrepaStudent>> getAllPrepaStudents() {
        return prepaStudentService.getAllPrepaStudents();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrepaStudentById(@PathVariable Long id) {
        return prepaStudentService.deletePrepaStudentById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrepaStudent> updatePrepaStudent(@PathVariable Long id, @RequestBody PrepaStudent updatedEntity) {
        return prepaStudentService.updatePrepaStudent(id, updatedEntity);
    }

    @GetMapping("/assignGroups")
    public void assignGroups() {
        System.out.println("Assigning the Groupe, my G");
        prepaStudentService.assignGroups();
    }
    

}
