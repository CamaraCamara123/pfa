package com.example.pfa.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class IngStudent extends Student {

    @ManyToOne
    private FiliereSemestre filiereSemestre;

    // @JsonIgnoreProperties({ "students" })
    @ManyToOne
    private GroupeProject groupeProject;

    @OneToOne
    private PFE pfe;

    @Override
    public String toString() {
        return "IngStudent{" +
                "id=" + getId() +
                '}';
    }

}
