package com.example.pfa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pfa.entities.Filiere;
import com.example.pfa.entities.FiliereSemestre;
import com.example.pfa.entities.Semestre;
import com.example.pfa.repositories.FiliereRepository;
import com.example.pfa.repositories.FiliereSemestreRepository;
import com.example.pfa.repositories.SemestreRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class FiliereService {

    @Autowired
    private FiliereRepository filiereRepository;

    @Autowired
    private FiliereSemestreRepository filiereSemestreRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    // CRUD methods
    public ResponseEntity<Filiere> saveFiliere(Filiere filiere) {
        Filiere savedEntity = filiereRepository.save(filiere);
        List<Semestre> semestres = semestreRepository.findAll();
        for(Semestre s : semestres){
            filiereSemestreRepository.save(new FiliereSemestre(null, savedEntity, s, null));
        }
        

        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Filiere> getFiliereById(Long id) {
        Filiere foundEntity = filiereRepository.findById(id).orElse(null);
        return foundEntity != null ? ResponseEntity.ok(foundEntity) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Filiere>> getAllFilieres() {
        List<Filiere> entities = filiereRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteFiliereById(Long id) {
        List<FiliereSemestre> fs = filiereRepository.fsByFiliere(id);
        fs.forEach(ffs->{
            filiereSemestreRepository.delete(ffs);
        });
        filiereRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Filiere> updateFiliere(Long id, Filiere updatedEntity) {
        if (!filiereRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Filiere result = filiereRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }
}
