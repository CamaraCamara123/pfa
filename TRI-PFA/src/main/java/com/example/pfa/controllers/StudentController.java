package com.example.pfa.controllers;

import com.example.pfa.entities.IngStudent;
import com.example.pfa.entities.Student;
import com.example.pfa.repositories.StudentRepository;
import com.example.pfa.services.StudentService;
import com.example.pfa.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@CrossOrigin("*")
public class StudentController {


    @Autowired
    StudentService studentService;


    @GetMapping("/fiche")
    public ResponseEntity<Student> getFicheEtudiant() {
        Student student = (Student) AuthenticationUtils.getCurrentlyAuthenticatedUser();
        return ResponseEntity.ok(student);
    }

    @PutMapping("/update")
    public ResponseEntity<Student> updateIngStudent(@RequestBody Student updatedEntity) {
        Student student=(Student) AuthenticationUtils.getCurrentlyAuthenticatedUser();
        return studentService.updateStudent(student.getId(),updatedEntity);

    }

}
