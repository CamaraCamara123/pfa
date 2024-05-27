package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.pfa.entities.Stage;
import com.example.pfa.services.FileUploadImpl;
import com.example.pfa.services.StageService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/stages")
@CrossOrigin("*")
public class StageController {
    @Autowired
    private StageService stageService;

    @Autowired
    private FileUploadImpl fileUploadImpl;

    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Stage> saveStage(@PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate,
            @RequestParam("encadrantTechnique") String encadrantTechnique,
            @RequestParam("entreprise") String entreprise,
            @RequestParam("ville") String ville) throws IOException {
        Stage stage = new Stage(null, title, description, startDate, endDate, encadrantTechnique, entreprise, ville,
                null);
        return stageService.saveStage(stage, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stage> getStageById(@PathVariable Long id) {
        return stageService.getStageById(id);
    }

    @GetMapping
    public ResponseEntity<List<Stage>> getAllStages() {
        return stageService.getAllStages();
    }

    @GetMapping("/byStudent/{id}")
    public ResponseEntity<List<Stage>> getStagesByStudent(@PathVariable Long id) {
        return stageService.getStagesByStudent(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStageById(@PathVariable Long id) {
        return stageService.deleteStageById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stage> updateStage(@PathVariable("id") Long id,
            @RequestParam("rapport") MultipartFile rapport) throws Exception {
        return stageService.updateStage(id, rapport);
    }

}