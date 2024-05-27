package com.example.pfa.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {


    @GetMapping("/admin")
    public String helloAdmin(){
        return "Hello Admin";
    }
    @GetMapping("/etudiant")
    public String helloEtudiant(){
        return "Hello Etudiant";
    }
    @GetMapping("/professeur")
    public String helloProfesseur(){
        return "Hello Professeur";
    }
}
