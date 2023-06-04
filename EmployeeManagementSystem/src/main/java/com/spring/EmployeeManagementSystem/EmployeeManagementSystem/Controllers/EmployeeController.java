package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.DAO.EmployeeDAO;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Services.EmployeeService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeDAO employeeDAO;

    public EmployeeController(EmployeeService employeeService, EmployeeDAO employeeDAO) {
        this.employeeService = employeeService;
        this.employeeDAO = employeeDAO;
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/name/{name}")
    public ResponseEntity<Employee> getEmployeeByName(@PathVariable String name) {
        Employee employee = employeeDAO.getEmployeeByName(name);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/employee/search/{name}")
    public ResponseEntity<List<Employee>> searchEmployeeByName(@PathVariable String name) {
        List<Employee> employees = employeeDAO.searchEmployeeByName(name);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/employee")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee) {
        Employee updatedEmployee = employeeDAO.updateEmployee(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // this function to check if email already exist in database or not, if exist
    // throw exception.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleEmailAlreadyExistException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exist");
    }
}
