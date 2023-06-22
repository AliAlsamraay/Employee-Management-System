package com.spring.EmployeeManagementSystem.Projections;

public interface EmployeeProjection {
    Long getId();

    String getName();

    String getEmail();

    String getDesignation();

    String getDepartment();

    Integer getSalary();
}