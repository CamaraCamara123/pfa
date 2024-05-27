package com.example.pfa.controllers;

import com.example.pfa.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.FiliereSemestre;
import com.example.pfa.entities.IngStudent;
import com.example.pfa.entities.PFE;
import com.example.pfa.entities.Stage;
import com.example.pfa.services.IngStudentService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ingStudents")
@CrossOrigin("*")
public class IngStudentController {
    @Autowired
    private IngStudentService ingStudentService;

    // CRUD endpoints
    @PostMapping("/admin/save")
    public ResponseEntity<IngStudent> saveIngStudent(@RequestBody IngStudent ingStudent,
            @RequestParam("createdAt") LocalDate createdAt) {
        ingStudent.setCreatedAt(createdAt);
        return ingStudentService.saveIngStudent(ingStudent);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<IngStudent> getIngStudentById(@PathVariable Long id) {
        return ingStudentService.getIngStudentById(id);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<IngStudent>> getAllIngStudents() {
        return ingStudentService.getAllIngStudents();
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> deleteIngStudentById(@PathVariable Long id) {
        return ingStudentService.deleteIngStudentById(id);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<IngStudent> updateIngStudent(@PathVariable Long id, @RequestBody IngStudent updatedEntity) {
        return ingStudentService.updateIngStudent(id, updatedEntity);
    }

    @PostMapping("/admin/addStage/{id}")
    public ResponseEntity<IngStudent> addStage(@PathVariable Long id, @RequestBody Stage stage) {
        return ingStudentService.addStage(id, stage);
    }

    @PostMapping("/admin/addPFE/{id}")
    public ResponseEntity<IngStudent> addPFE(@PathVariable Long id, @RequestBody PFE pfe) {
        return ingStudentService.addPFE(id, pfe);
    }

    @GetMapping("/admin/byFiliereSemestre/{id}")
    public ResponseEntity<List<IngStudent>> addStage(@PathVariable Long id) {
        return ingStudentService.getIngStudentsByFiliereSemestre(id);
    }

}