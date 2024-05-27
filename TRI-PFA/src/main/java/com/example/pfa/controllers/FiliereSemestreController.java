package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.FiliereSemestre;
import com.example.pfa.services.FiliereSemestreService;

import java.util.List;

@RestController
@RequestMapping("/filiere_semestre")
@CrossOrigin("*")
public class FiliereSemestreController {
    @Autowired
    private FiliereSemestreService filiereSemestreService;

    // CRUD endpoints
    @PostMapping
    public ResponseEntity<FiliereSemestre> saveFiliereSemestre(@RequestBody FiliereSemestre filiereSemestre) {
        return filiereSemestreService.saveFiliereSemestre(filiereSemestre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FiliereSemestre> getFiliereSemestreById(@PathVariable Long id) {
        return filiereSemestreService.getFiliereSemestreById(id);
    }

    @GetMapping
    public ResponseEntity<List<FiliereSemestre>> getAllFiliereSemestres() {
        return filiereSemestreService.getAllFiliereSemestres();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFiliereSemestreById(@PathVariable Long id) {
        return filiereSemestreService.deleteFiliereSemestreById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FiliereSemestre> updateFiliereSemestre(@PathVariable Long id,
            @RequestBody FiliereSemestre updatedEntity) {
        return filiereSemestreService.updateFiliereSemestre(id, updatedEntity);
    }

    @GetMapping("/semestre/{id}")
    public ResponseEntity<FiliereSemestre> getFiliereSemestreBySemestre(@PathVariable Long id) {
        return filiereSemestreService.getFiliereSemestreBySemestre(id);
    }

    @GetMapping("/filiere/{id}")
    public ResponseEntity<FiliereSemestre> getFiliereSemestreByFiliere(@PathVariable Long id) {
        return filiereSemestreService.getFiliereSemestreBySemestre(id);
    }

}
