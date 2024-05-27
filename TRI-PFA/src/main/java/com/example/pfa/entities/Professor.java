package com.example.pfa.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Professor extends User {
    private int nbGroupe = 0;

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + getId() +
                '}';
    }
}
