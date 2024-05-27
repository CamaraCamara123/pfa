package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.Element;
import com.example.pfa.entities.Module;
import com.example.pfa.services.ModuleService;

import java.util.List;

@RestController
@RequestMapping("/modules")
@CrossOrigin("*")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;


    // CRUD endpoints
    @PostMapping
    public ResponseEntity<Module> saveModule(@RequestBody Module module) {
        return moduleService.saveModule(module);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id);
    }

    @GetMapping
    public ResponseEntity<List<Module>> getAllModules() {
        return moduleService.getAllModules();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModuleById(@PathVariable Long id) {
        return moduleService.deleteModuleById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable Long id, @RequestBody Module updatedEntity) {
        return moduleService.updateModule(id, updatedEntity);
    }

    @PostMapping("/element/add/{id}")
    public ResponseEntity<Module> addElementInModule(@RequestBody Element element,@PathVariable Long id) {
        return moduleService.addElement(id,element);
    }
}
