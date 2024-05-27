package com.example.pfa.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stage extends Project {

    @ManyToOne
    private IngStudent ingStudent;

    private String title;

    private String description;

    private LocalDate start_date;

    private LocalDate end_date;

    private String encadrantTechnique;

    private String entreprise;

    private String ville;
    
    private String urlRapport;

    
}
