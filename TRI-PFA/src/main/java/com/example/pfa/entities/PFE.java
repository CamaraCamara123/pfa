package com.example.pfa.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PFE extends Project {

    private String title;

    private String description;

    private LocalDate start_date;

    private LocalDate end_date;

    private String encadrantTechnique;

    private String entreprise;

    private String ville;

    @ManyToOne(fetch = FetchType.EAGER)
    // @JsonInclude(JsonInclude.Include.NON_NULL)
    private Professor encadrantAcademique;

    private String urlRapport;

}
