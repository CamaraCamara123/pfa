package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.GroupeProject;
import com.example.pfa.entities.IngStudent;
import com.example.pfa.entities.PFE;
import com.example.pfa.entities.Professor;
import com.example.pfa.repositories.GroupeProjectRepository;
import com.example.pfa.repositories.IngStudentRepository;
import com.example.pfa.repositories.PFARepository;
import com.example.pfa.repositories.ProfessorRepository;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupeProjectService {

    @Autowired
    private GroupeProjectRepository groupeProjectRepository;

    @Autowired
    private PFARepository pfaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private IngStudentRepository ingStudentRepository;

    // CRUD methods
    public ResponseEntity<GroupeProject> saveGroupeProject(GroupeProject groupeProject) {

        List<IngStudent> students = groupeProject.getStudents();
        for (int index = 0; index < groupeProject.getStudents().size(); index++) {
            students.set(index, ingStudentRepository.getById(students.get(index).getId()));
        }
        groupeProject.setStudents(students);
        groupeProject.setEncadrant(null);
        // groupeProject.setFiliereSemestre(null);
        groupeProject.setPfa(null);
        GroupeProject gp = groupeProjectRepository.save(groupeProject);
        students.forEach(st -> {
            st.setGroupeProject(gp);
            ingStudentRepository.save(st);
        });
        System.out.println(groupeProject);
        return ResponseEntity.ok(groupeProject);
    }

    public ResponseEntity<GroupeProject> getGroupeProjectById(Long id) {
        GroupeProject foundEntity = groupeProjectRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<GroupeProject>> getAllGroupeProjects() {
        List<GroupeProject> entities = groupeProjectRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteGroupeProjectById(Long id) {
        GroupeProject gps = groupeProjectRepository.getById(id);
        for (IngStudent st : gps.getStudents()) {
            st.setGroupeProject(null);
            ingStudentRepository.save(st);
        }
        groupeProjectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<GroupeProject> updateGroupeProject(Long id, GroupeProject updatedEntity) {
        if (!groupeProjectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        if (updatedEntity.getEncadrant().getId() == null) {
            updatedEntity.setEncadrant(null);
        }
        if (updatedEntity.getPfa().getId() == null) {
            updatedEntity.setPfa(null);
        }
        GroupeProject result = groupeProjectRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<GroupeProject>> getGroupeProjectsByProfessor(Long id) {
        List<GroupeProject> entities = groupeProjectRepository.findGroupeProjectsByProfessor(id);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<GroupeProject> getGroupeProjectByIngStudent(Long id) {
        GroupeProject foundEntity = groupeProjectRepository.findGroupeProjectByIngStudent(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<GroupeProject> getGroupeProjectByPFA(Long id) {
        GroupeProject foundEntity = groupeProjectRepository.findGroupeProjectByPFA(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<GroupeProject>> getGroupeProjectsByFiliereSemestre(Long id) {
        List<GroupeProject> entities = groupeProjectRepository.findGroupeProjectsByFiliereSemestre(id);
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<List<GroupeProject>> affectationEncadrant() {
        List<GroupeProject> gps = groupeProjectRepository.findAll();
        List<Professor> profs = professorRepository.findAll();
        List<Professor> profs2 = new ArrayList<>();
        List<Professor> profs3 = new ArrayList<>();
        List<Professor> profsFinale = new ArrayList<>();
        Collections.shuffle(profs);
        for (GroupeProject gp : gps) {
            if (!profs.isEmpty()) {
                Professor profAssigned = profs.remove(0);
                gp.setEncadrant(profAssigned);
                profAssigned.setNbGroupe(profAssigned.getNbGroupe() + 1);
                profs2.add(profAssigned);
            } else if (profs.isEmpty() && !profs2.isEmpty()) {
                Professor profAssigned = profs2.remove(0);
                gp.setEncadrant(profAssigned);
                profAssigned.setNbGroupe(profAssigned.getNbGroupe() + 1);
                profs3.add(profAssigned);
            } else if (profs.isEmpty() && profs2.isEmpty() && !profs3.isEmpty()) {
                Professor profAssigned = profs3.remove(0);
                gp.setEncadrant(profAssigned);
                profAssigned.setNbGroupe(profAssigned.getNbGroupe() + 1);
                profs3.add(profAssigned);
            }
            groupeProjectRepository.save(gp);
        }
        profs.forEach(p -> {
            profsFinale.add(p);
        });

        profs2.forEach(p -> {
            profsFinale.add(p);
        });

        profs.forEach(p -> {
            profsFinale.add(p);
        });

        for (Professor pf : profsFinale) {
            professorRepository.save(pf);
        }
        List<GroupeProject> grps = groupeProjectRepository.findAll();
        return ResponseEntity.ok(grps);
    }

    public ResponseEntity<GroupeProject> changeEncadrant(Long groupe_id, Long encadrant_id) {
        Professor p = professorRepository.findById(encadrant_id).get();
        GroupeProject entity = groupeProjectRepository.findById(groupe_id).get();
        entity.setEncadrant(p);
        entity = groupeProjectRepository.save(entity);
        
        return ResponseEntity.ok(entity);
    }

}