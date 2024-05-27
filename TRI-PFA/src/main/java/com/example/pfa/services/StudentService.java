package com.example.pfa.services;

import com.example.pfa.entities.Student;
import com.example.pfa.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // CRUD methods
    public ResponseEntity<Student> saveStudent(Student student) {
        Student savedEntity = studentRepository.save(student);
        return ResponseEntity.ok(savedEntity);
    }

    public ResponseEntity<Student> getStudentById(Long id) {
        Optional<Student> foundEntity = studentRepository.findById(id);
        return foundEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> entities = studentRepository.findAll();
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<Void> deleteStudentById(Long id) {
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Student> updateStudent(Long id, Student updatedEntity) {
        if (!studentRepository.existsById(id)) {
            updatedEntity.setUpdatedAt(LocalDate.now());
            return ResponseEntity.notFound().build();
        }
        updatedEntity.setId(id);
        Student result = studentRepository.save(updatedEntity);
        return ResponseEntity.ok(result);
    }
}