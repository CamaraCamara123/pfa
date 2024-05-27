package com.example.pfa.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Administrator extends User {
    
    @OneToMany
    private List<TimeTable> timeTables;
}
