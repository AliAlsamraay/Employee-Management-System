package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
