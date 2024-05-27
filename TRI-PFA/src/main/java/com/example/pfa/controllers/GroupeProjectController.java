package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.GroupeProject;
import com.example.pfa.entities.PFE;
import com.example.pfa.services.GroupeProjectService;

import java.util.List;

@RestController
@RequestMapping("/groupe_projects")
@CrossOrigin("*")
public class GroupeProjectController {
    @Autowired
    private GroupeProjectService groupeProjectService;


    // CRUD endpoints
    @PostMapping
    public ResponseEntity<String> saveGroupeProject(@RequestBody GroupeProject groupeProject) {
        System.out.println(groupeProject);
        groupeProjectService.saveGroupeProject(groupeProject);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupeProject> getGroupeProjectById(@PathVariable Long id) {
        return groupeProjectService.getGroupeProjectById(id);
    }

    @GetMapping
    public ResponseEntity<List<GroupeProject>> getAllGroupeProjects() {
        return groupeProjectService.getAllGroupeProjects();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupeProjectById(@PathVariable Long id) {
        return groupeProjectService.deleteGroupeProjectById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupeProject> updateGroupeProject(@PathVariable Long id, @RequestBody GroupeProject updatedEntity) {
        return groupeProjectService.updateGroupeProject(id, updatedEntity);
    }

    @GetMapping("/encadrant/{id}")
    public ResponseEntity<List<GroupeProject>> getGroupeProjectByProfessor(@PathVariable Long id) {
        return groupeProjectService.getGroupeProjectsByProfessor(id);
    }

    @GetMapping("/filiereSemestre/{id}")
    public ResponseEntity<List<GroupeProject>> getGroupeProjectByFiliereSemestre(@PathVariable Long id) {
        return groupeProjectService.getGroupeProjectsByFiliereSemestre(id);
    }

    @GetMapping("/student/groupeProject/{id}")
    public ResponseEntity<GroupeProject> getGroupeProjectByIngStudent(@RequestParam Long id) {
        return groupeProjectService.getGroupeProjectByIngStudent(id);
    }

    @GetMapping("/affectation")
    public ResponseEntity<List<GroupeProject>> affectationEncadrant() {
        return groupeProjectService.affectationEncadrant();
    }

    @PostMapping("/change/{groupe_id}/{encadrant_id}")
    public ResponseEntity<GroupeProject> changeEncadrant(@PathVariable Long groupe_id,
            @PathVariable Long encadrant_id) {
        return groupeProjectService.changeEncadrant(groupe_id, encadrant_id);
    }

}
