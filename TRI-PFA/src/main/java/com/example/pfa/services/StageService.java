package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.pfa.entities.IngStudent;
import com.example.pfa.entities.PFE;
import com.example.pfa.entities.Stage;
import com.example.pfa.repositories.IngStudentRepository;
import com.example.pfa.repositories.StageRepository;

import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class StageService {

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private FileUploadImpl fileUploadImpl;

    @Autowired
    IngStudentRepository ingStudentRepository;

    // CRUD methods
    public ResponseEntity<Stage> saveStage(Stage stage, Long id) {

        IngStudent ing = ingStudentRepository.getById(id);
        stage.setIngStudent(ing);
        Stage savedEntity = stageRepository.save(stage);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Stage> getStageById(Long id) {
        Stage foundEntity = stageRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Stage>> getAllStages() {
        List<Stage> entities = stageRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteStageById(Long id) {
        // IngStudent ing = ingStudentRepository.findById(idStudent).get();
        // ingStudentRepository.save(ing);
        stageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Stage> updateStage(Long id, MultipartFile rapport) throws Exception {
        if (!stageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Stage stage = stageRepository.findById(id).get();
        if (stage.getUrlRapport() !=null) {
            fileUploadImpl.deleteFile(stage.getUrlRapport());
        } 
        String url = fileUploadImpl.uploadFile(rapport);
        stage.setUrlRapport(url);
        Stage result = stageRepository.save(stage);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<Stage>> getStagesByStudent(Long id) {
        List<Stage> stages = stageRepository.getStagesByStudent(id);
        return ResponseEntity.ok(stages);
    }
}