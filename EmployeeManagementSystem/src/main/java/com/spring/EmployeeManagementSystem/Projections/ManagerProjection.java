package com.spring.EmployeeManagementSystem.Projections;

import java.util.List;

public interface ManagerProjection {
    Long getId();

    String getName();

    String getEmail();

    String getDepartment();

    List<EmployeeProjection> getEmployees();
}
