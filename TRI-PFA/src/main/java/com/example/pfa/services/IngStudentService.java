package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.FiliereSemestre;
import com.example.pfa.entities.IngStudent;
import com.example.pfa.entities.PFE;
import com.example.pfa.entities.Role;
import com.example.pfa.entities.Stage;
import com.example.pfa.repositories.FiliereSemestreRepository;
import com.example.pfa.repositories.IngStudentRepository;
import com.example.pfa.repositories.PFERepository;
import com.example.pfa.repositories.RoleRepository;
import com.example.pfa.repositories.StageRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IngStudentService {

    @Autowired
    private IngStudentRepository ingStudentRepository;

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private PFERepository pfeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    private FiliereSemestreRepository filiereSemestreRepository;

    // CRUD methods
    public ResponseEntity<IngStudent> saveIngStudent(IngStudent ingStudent) {

        ingStudent.setPassword(passwordEncoder.encode(ingStudent.getPassword()));
        ingStudent.setEnabled(true);
        Role role = roleRepository.findById(1L).get();
        ingStudent.setRoles(Set.of(role));
        IngStudent savedEntity = ingStudentRepository.save(ingStudent);
        System.out.println(savedEntity);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<IngStudent> getIngStudentById(Long id) {
        IngStudent foundEntity = ingStudentRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<IngStudent>> getAllIngStudents() {
        List<IngStudent> entities = ingStudentRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteIngStudentById(Long id) {
        ingStudentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<IngStudent> updateIngStudent(Long id, IngStudent updatedEntity) {
        if (!ingStudentRepository.existsById(id)) {
            updatedEntity.setUpdatedAt(LocalDate.now());
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        updatedEntity.setEnabled(true);
        IngStudent result = ingStudentRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<IngStudent>> getIngStudentsByFiliereSemestre(Long id) {
        List<IngStudent> entities = ingStudentRepository.findIngStudentsByFiliereSemestre(id);
        System.out.println(entities);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<IngStudent> addStage(Long id, Stage stage) {
        IngStudent ingStudent = ingStudentRepository.findById(id).get();
        stage.setIngStudent(ingStudent);
        stageRepository.save(stage);
        return ResponseEntity.ok(ingStudent);
    }

    public ResponseEntity<IngStudent> addPFE(Long id, PFE pfe) {
        pfe.setEncadrantAcademique(null);
        PFE entity = pfeRepository.save(pfe);
        IngStudent ing = ingStudentRepository.findById(id).get();
        ing.setPfe(entity);
        ingStudentRepository.save(ing);
        return ResponseEntity.ok(ing);
    }

}
