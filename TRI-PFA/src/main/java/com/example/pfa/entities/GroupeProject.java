package com.example.pfa.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class GroupeProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({ "groupeProject" })
    @OneToMany
    private List<IngStudent> students;

    @ManyToOne
    private Professor encadrant;

    @OneToOne
    private PFA pfa;

    @ManyToOne
    private FiliereSemestre filiereSemestre;

    @Override
    public String toString() {
        return "GroupeProject{" +
                "id=" + id +
                '}';
    }

}
