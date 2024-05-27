package com.example.pfa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PrepaStudent extends Student {

    @ManyToOne
    private Semestre semestre;

}
