package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.pfa.entities.PFA;
import com.example.pfa.entities.PFE;
import com.example.pfa.services.FileUploadImpl;
import com.example.pfa.services.PFAService;
import com.example.pfa.services.PFEService;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/pfes")
@CrossOrigin("*")
public class PFEController {
    @Autowired
    private PFEService pfeService;

    @Autowired
    private FileUploadImpl fileUploadImpl;

    // CRUD endpoints
    @PostMapping("/{id}")
    public ResponseEntity<PFE> savePFE(@PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate,
            @RequestParam("encadrantTechnique") String encadrantTechnique,
            @RequestParam("entreprise") String entreprise,
            @RequestParam("ville") String ville) {
        PFE pfe = new PFE(title, description, startDate, endDate, encadrantTechnique, entreprise, ville, null, null);
        return pfeService.savePFE(pfe, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PFE> getPFEById(@PathVariable Long id) {
        return pfeService.getPFEById(id);
    }

    @GetMapping
    public ResponseEntity<List<PFE>> getAllPFEs() {
        return pfeService.getAllPFEs();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePFEById(@PathVariable Long id) {
        return pfeService.deletePFEById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PFE> updatePFE(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate,
            @RequestParam("encadrantTechnique") String encadrantTechnique,
            @RequestParam("entreprise") String entreprise,
            @RequestParam("ville") String ville) throws Exception {
        PFE pfe = new PFE(title, description, startDate, endDate, encadrantTechnique, entreprise, ville, null, null);
        return pfeService.updatePFE(id, pfe);
    }

    @PutMapping("/uploadRapport/{id}")
    public ResponseEntity<PFE> uploadRapport(
            @PathVariable("id") Long id,
            @RequestParam("rapport") MultipartFile rapport) throws Exception {
        System.out.println("file :::::::: " + rapport.getBytes());
        return pfeService.upload(id, rapport);
    }

    @GetMapping("/encadrant/{id}")
    public ResponseEntity<List<PFE>> getEncadrantPFEs(@PathVariable Long id) {
        return pfeService.getEncadrantPFEs(id);
    }

    @GetMapping("/affectations")
    public ResponseEntity<List<PFE>> AffectationEncadrant() {
        return pfeService.affectations();
    }

    @PostMapping("/change/{pfe_id}/{encadrant_id}")
    public ResponseEntity<PFE> changeEncadrant(@PathVariable Long pfe_id,
            @PathVariable Long encadrant_id) {
        return pfeService.changeEncadrant(pfe_id, encadrant_id);
    }
}
