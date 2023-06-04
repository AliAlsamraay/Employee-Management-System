package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.DAO;

import java.util.List;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.Employee;

public interface EmployeeDAO {
    public void saveEmployee(Employee employee);

    public Employee getEmployeeById(Long id);

    public List<Employee> getAllEmployees();

    public Employee getEmployeeByName(String name);

    public List<Employee> searchEmployeeByName(String name);

    public Employee updateEmployee(Employee employee);

    public void deleteEmployee(Long id);

    public void deleteAllEmployees();
}
