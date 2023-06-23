package com.spring.EmployeeManagementSystem.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spring.EmployeeManagementSystem.Deserializers.AttendanceStatusDeserializer;
import com.spring.EmployeeManagementSystem.Enums.AttendanceStatus;
import com.spring.EmployeeManagementSystem.validators.ValidateAttendanceStatus;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, })
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    @Column(name = "date")
    private LocalDate date;

    @NotNull(message = "Status is required")
    @ValidateAttendanceStatus(message = "Invalid attendance status provided")
    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = AttendanceStatusDeserializer.class)
    @Column(name = "status")
    private AttendanceStatus status;

    // Constructors
    public Attendance() {
    }

    public Attendance(Employee employee, LocalDate date, AttendanceStatus status) {
        this.employee = employee;
        this.date = date;
        this.status = status;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

}
