package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.pfa.entities.PFA;
import com.example.pfa.services.FileUploadImpl;
import com.example.pfa.services.PFAService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pfas")
@CrossOrigin("*")
public class PFAController {
    @Autowired
    private PFAService pfaService;

    @Autowired
    private FileUploadImpl fileUploadImpl;

    // CRUD endpoints
    @PostMapping("/{id}")
    public ResponseEntity<PFA> savePFA(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate) throws IOException {

        PFA pfa = new PFA(title, description, startDate, endDate, null);
        // if (rapport != null) {
        // String fileUrl = fileUploadImpl.uploadFile(rapport);
        // pfa.setUrl(fileUrl);
        // }
        return pfaService.savePFA(pfa, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PFA> getPFAById(@PathVariable Long id) {
        return pfaService.getPFAById(id);
    }

    @GetMapping
    public ResponseEntity<List<PFA>> getAllPFAs() {
        return pfaService.getAllPFAs();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePFAById(@PathVariable Long id) {
        return pfaService.deletePFAById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PFA> updatePFA(@PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("start_date") LocalDate startDate,
            @RequestParam("end_date") LocalDate endDate,
            @RequestParam("rapport") MultipartFile rapport) throws Exception {
        PFA pfa = new PFA();
        pfa.setTitle(title);
        pfa.setDescription(description);
        pfa.setStart_date(startDate);
        pfa.setEnd_date(endDate);
        pfa.setUrl("");
        if (rapport != null) {
            String fileUrl = fileUploadImpl.uploadFile(rapport);
            pfa.setUrl(fileUrl);
        }
        return pfaService.updatePFA(id, pfa);
    }
}
