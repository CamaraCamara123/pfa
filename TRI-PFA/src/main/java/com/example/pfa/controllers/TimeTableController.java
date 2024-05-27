package com.example.pfa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pfa.entities.TimeTable;
import com.example.pfa.services.TimeTableService;

import java.util.List;

@RestController
@RequestMapping("/timetables")
@CrossOrigin("*")
public class TimeTableController {
    @Autowired
    private TimeTableService timeTableService;


    // CRUD endpoints
    @PostMapping
    public ResponseEntity<TimeTable> saveTimeTable(@RequestBody TimeTable timeTable) {
        return timeTableService.saveTimeTable(timeTable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeTable> getTimeTableById(@PathVariable Long id) {
        return timeTableService.getTimeTableById(id);
    }

    @GetMapping
    public ResponseEntity<List<TimeTable>> getAllTimeTables() {
        return timeTableService.getAllTimeTables();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeTableById(@PathVariable Long id) {
        return timeTableService.deleteTimeTableById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeTable> updateTimeTable(@PathVariable Long id, @RequestBody TimeTable updatedEntity) {
        return timeTableService.updateTimeTable(id, updatedEntity);
    }

    @GetMapping("/section/{id}")
    public ResponseEntity<TimeTable> getTimeTableBySection(@PathVariable Long id) {
        return timeTableService.getTimeTableBySection(id);
    }
}
