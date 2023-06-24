package com.spring.EmployeeManagementSystem.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.EmployeeManagementSystem.Entities.PerformanceEvaluation;
import com.spring.EmployeeManagementSystem.Services.PerformanceEvaluationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/evaluations")
public class PerformanceEvaluationController {
    private final PerformanceEvaluationService evaluationService;

    public PerformanceEvaluationController(PerformanceEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping
    public ResponseEntity<PerformanceEvaluation> createEvaluation(
            @RequestParam(name = "employeeId", required = true) Long employeeId,
            @RequestParam(name = "managerId", required = true) Long managerId,
            @Valid @RequestBody PerformanceEvaluation evaluation) {
        PerformanceEvaluation createdEvaluation = evaluationService.createEvaluation(employeeId, managerId, evaluation);
        return ResponseEntity.ok(createdEvaluation);
    }

    @GetMapping
    public ResponseEntity<List<PerformanceEvaluation>> getEvaluationsByEmployeeId(
            @RequestParam(name = "employeeId", required = true) Long employeeId) {
        List<PerformanceEvaluation> evaluations = evaluationService.getEvaluationsByEmployeeId(employeeId);
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/estimated")
    public ResponseEntity<PerformanceEvaluation> getEstimatedEvaluationsByEmployeeId(
            @RequestParam(name = "employeeId", required = true) Long employeeId) {
        PerformanceEvaluation evaluation = evaluationService.calculatePerformanceEvaluation(employeeId);
        return ResponseEntity.ok(evaluation);
    }
}
