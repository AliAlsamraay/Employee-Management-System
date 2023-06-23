package com.spring.EmployeeManagementSystem.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.Projections.EmployeeProjection;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);

    boolean existsById(Long id);

    boolean existsByName(String name);

    List<EmployeeProjection> findByManagerId(Long managerId);

    List<Employee> findAllByIdAndManagerId(Long employeeId, Long managerId);
}
