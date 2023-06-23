package com.spring.EmployeeManagementSystem.Controllers;

import com.spring.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.Projections.EmployeeProjection;
import com.spring.EmployeeManagementSystem.Services.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @Valid @RequestBody Employee employee,
            @RequestParam(name = "managerId", required = true) Long managerId) {
        final Employee savedEmployee = employeeService.saveEmployee(employee, managerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        final Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(employeeService.getPaginatedEmployees(pageable));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteEmployee(
            @RequestParam(name = "employeeId", required = true) Long employeeId,
            @RequestParam(name = "managerId", required = true) Long managerId) {
        employeeService.deleteEmployee(managerId, employeeId);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Employee> getEmployeeByName(@PathVariable String name) {
        Employee employee = employeeService.getEmployeeByName(name);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Employee>> searchEmployeeByName(@PathVariable String name) {
        List<Employee> employees = employeeService.searchEmployeeByName(name);
        return ResponseEntity.ok(employees);
    }

    @PutMapping
    public ResponseEntity<Employee> updateEmployee(
            @RequestParam(name = "managerId", required = true) Long managerId,
            @RequestBody Employee employee) {
        final Employee updatedEmployee = employeeService.updateEmployee(managerId, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/manager/{id}")
    public ResponseEntity<List<EmployeeProjection>> getEmployeesByManagerId(@PathVariable Long id) {
        List<EmployeeProjection> employees = employeeService.getEmployeesByManagerId(id);
        return ResponseEntity.ok(employees);
    }

}
