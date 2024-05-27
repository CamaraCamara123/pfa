package com.example.pfa.controllers;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.aspectj.weaver.ast.Test;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pfa.entities.TimePlanner;

@RestController
@RequestMapping("/solver")
@CrossOrigin("*")
public class TimePlannerContoller {

    // Just the definition here
    
    @Autowired
    private SolverManager<TimePlanner, UUID> solverManager;
    
    @PostMapping("/solve")
    public TimePlanner solve(@RequestBody TimePlanner problem) {
        UUID problemId = UUID.randomUUID();
        // Submit the problem to start solving
        SolverJob<TimePlanner, UUID> solverJob = solverManager.solve(problemId, problem);
        TimePlanner solution;
        try {
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }

        System.err.println(solution);
        return solution;
    }


}
