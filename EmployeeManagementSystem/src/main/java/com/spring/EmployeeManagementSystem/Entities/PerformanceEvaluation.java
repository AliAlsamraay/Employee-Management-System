package com.spring.EmployeeManagementSystem.Entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "performance_evaluations")
public class PerformanceEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, })
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    @NotNull(message = "Evaluation date is required")
    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be greater than or equal to 1")
    @Max(value = 5, message = "Rating must be less than or equal to 5")
    @Column(name = "rating")
    private double rating;

    @Column(name = "comment")
    private String comment;

    @NotNull(message = "Expected salary is required")
    @Column(name = "expected_salary")
    private double expectedSalary;

    // Constructors, getters, and setters
    public PerformanceEvaluation() {
    }

    public PerformanceEvaluation(Employee employee, LocalDate evaluationDate, double rating, double expectedSalary,
            String comment) {
        this.employee = employee;
        this.evaluationDate = evaluationDate;
        this.rating = rating;
        this.expectedSalary = expectedSalary;
        this.comment = comment;

    }

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public double getRating() {
        return rating;
    }

    public double getExpectedSalary() {
        return expectedSalary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setExpectedSalary(double expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
