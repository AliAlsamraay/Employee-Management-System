package com.spring.EmployeeManagementSystem.Entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spring.EmployeeManagementSystem.Deserializers.LeaveRequestStatusDeserializer;
import com.spring.EmployeeManagementSystem.Enums.LeaveRequestStatus;
import com.spring.EmployeeManagementSystem.validators.ValidateLeaveRequestStatus;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "leave_request")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, })
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    @Column(name = "start_date")
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Column(name = "end_date")
    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Column(name = "reason")
    @NotBlank(message = "Reason is required")
    private String reason;

    @Enumerated(EnumType.STRING)
    @ValidateLeaveRequestStatus(message = "Invalid leave request status provided")
    @JsonDeserialize(using = LeaveRequestStatusDeserializer.class, as = LeaveRequestStatus.class)
    @Column(name = "status")
    private LeaveRequestStatus status;

    // Constructors, getters, and setters
    public LeaveRequest() {
        // if (this.status == null) {
        // this.status = LeaveRequestStatus.PENDING;
        // }
    }

    public LeaveRequest(Long id, Employee employee, LocalDate startDate, LocalDate endDate, String reason,
            LeaveRequestStatus status) {
        this.id = id;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = LeaveRequestStatus.PENDING;
        }
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStatus(LeaveRequestStatus status) {
        this.status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    // getters
    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LeaveRequestStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    // toString
    @Override
    public String toString() {
        return "LeaveRequest [employee=" + employee + ", endDate=" + endDate + ", id=" + id + ", reason=" + reason
                + ", startDate=" + startDate + ", status=" + status + "]";
    }

}
