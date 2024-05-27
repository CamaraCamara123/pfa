package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.Administrator;
import com.example.pfa.services.AdministratorService;

import java.util.List;

@RestController
@RequestMapping("/administrators")
@CrossOrigin("*")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;


    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Administrator> saveAdministrator(@RequestBody Administrator administrator) {
        return administratorService.saveAdministrator(administrator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getAdministratorById(@PathVariable Long id) {
        return administratorService.getAdministratorById(id);
    }

    @GetMapping
    public ResponseEntity<List<Administrator>> getAllAdministrators() {
        return administratorService.getAllAdministrators();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministratorById(@PathVariable Long id) {
        return administratorService.deleteAdministratorById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrator> updateAdministrator(@PathVariable Long id, @RequestBody Administrator updatedEntity) {
        return administratorService.updateAdministrator(id, updatedEntity);
    }
}