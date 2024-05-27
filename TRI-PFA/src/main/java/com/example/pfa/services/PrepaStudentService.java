package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Groupe;
import com.example.pfa.entities.PrepaStudent;
import com.example.pfa.entities.Section;
import com.example.pfa.entities.Semestre;
import com.example.pfa.repositories.PrepaStudentRepository;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PrepaStudentService {

    @Autowired
    private PrepaStudentRepository prepaStudentRepository;

    @Autowired
    private SemestreService semestreService;

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private SectionService sectionService;

    // CRUD methods
    public ResponseEntity<PrepaStudent> savePrepaStudent(PrepaStudent prepaStudent) {
        PrepaStudent savedEntity = prepaStudentRepository.save(prepaStudent);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<PrepaStudent> getPrepaStudentById(Long id) {
        PrepaStudent foundEntity = prepaStudentRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<PrepaStudent>> getAllPrepaStudents() {
        List<PrepaStudent> entities = prepaStudentRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<PrepaStudent> getPrepaStudentBycin(String cin) {
        PrepaStudent foundEntity = prepaStudentRepository.findPrepaStudentByCin(cin);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<PrepaStudent> getPrepaStudentByCne(String cne) {
        PrepaStudent foundEntity = prepaStudentRepository.findPrepaStudentByCne(cne);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> deletePrepaStudentById(Long id) {
        prepaStudentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<PrepaStudent> updatePrepaStudent(Long id, PrepaStudent updatedEntity) {
        if (!prepaStudentRepository.existsById(id)) {
            updatedEntity.setUpdatedAt(LocalDate.now());
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        PrepaStudent result = prepaStudentRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    // Assign the students automaticaly to the groups

    public void assignGroups() {
        System.out.println("Assigning the students to their groups");

        // Get all semesters
        List<Semestre> semestres = semestreService.getAllSemestres().getBody();

        // Max capacity for each section and group
        int maxCapacitySection = 5;
        int maxCapacityGroup = 2;

        for (Semestre semestre : semestres) {
            // Check if there are any students in the current semester
            List<PrepaStudent> studentsBySemestre = prepaStudentRepository.findBySemestre(semestre);
            if (studentsBySemestre.isEmpty()) {
                continue; // Skip to the next semester if there are no students
            }

            // Sort students by LastName
            Collections.sort(studentsBySemestre, Comparator.comparing(PrepaStudent::getLastName));

            int studentCount = 0;
            int sectionIndex = 1;

            // Initialize a section
            Section section = new Section();
            section.setSemestre(semestre);
            section.setName("Section " + sectionIndex);

            // Save the section
            section = sectionService.saveSection(section).getBody();

            // Iterate over students and assign them to groups within sections
            Groupe groupe = null;
            for (PrepaStudent student : studentsBySemestre) {
                studentCount++;

                // Create a new section if the current one is full
                if ((studentCount - 1) % maxCapacitySection == 0 && studentCount > 1) {
                    sectionIndex++;
                    section = new Section();
                    section.setSemestre(semestre);
                    section.setName("Section " + sectionIndex);

                    // Save the new section
                    section = sectionService.saveSection(section).getBody();
                }

                // Create a new group if necessary
                if ((studentCount - 1) % maxCapacityGroup == 0 || groupe == null) {
                    groupe = new Groupe();
                    groupe.setType("TD");
                    groupe.setMax_capacity(maxCapacityGroup);
                    groupe.setSection(section);
                    groupe.setPrepaStudents(new ArrayList<>()); // Set an empty list
                    groupe = groupeService.saveGroupe(groupe).getBody();
                }

                // Add the student to the group
                groupe.getPrepaStudents().add(student);
                groupeService.saveGroupe(groupe);
            }
        }
    }

}
