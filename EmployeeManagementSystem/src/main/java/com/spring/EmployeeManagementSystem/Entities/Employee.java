package com.spring.EmployeeManagementSystem.Entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Designation is required")
    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    @NotBlank(message = "Department is required")
    private String department;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Attendance> attendances = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, })
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private Manager manager;

    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be greater than 0")
    @Max(value = 1000000, message = "Salary must be less than 1000000")
    @Column(name = "salary")
    private Integer salary;

    // default constructor
    public Employee() {
    }

    public Employee(
            @NotBlank(message = "Name is required") String name,
            @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
            @NotBlank(message = "Designation is required") String designation,
            @NotBlank(message = "Department is required") String department,
            Integer salary) {
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.department = department;
        this.salary = salary;
    }

    // setters and getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendance(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    // add attendance to employee.
    public void addAttendance(Attendance attendance) {
        if (this.attendances == null) {
            this.attendances = new ArrayList<>();
        }
        this.attendances.add(attendance);
        attendance.setEmployee(this);
    }

    // remove attendance from employee.
    public void removeAttendance(Attendance attendance) {
        if (this.attendances != null) {
            this.attendances.remove(attendance);
            attendance.setEmployee(null);
        }
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;

    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    // toString method
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' + ", email='" + email + '\'' + ", designation='" + designation + '\''
                + ", department='" + department + '\'' + '}';

    }

}
