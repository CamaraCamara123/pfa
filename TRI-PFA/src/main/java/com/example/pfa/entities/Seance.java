package com.example.pfa.entities;

import java.util.List;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@PlanningEntity
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Element element;

    @ManyToOne
    @PlanningVariable
    private Salle salle;

    @ManyToOne
    private TimeTable timeTable;

    @ManyToOne
    @PlanningVariable
    private TimeSlot timeSlot;

    @ManyToMany
    private List<Groupe> groupes;

    // To design the type of the seance (Cours / TD / TP)
    private String type;
    
}
