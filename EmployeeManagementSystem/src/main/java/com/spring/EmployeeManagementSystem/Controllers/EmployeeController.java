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
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee employee,
            @RequestParam(required = true) Long managerId) {
        final Employee savedEmployee = employeeService.saveEmployee(employee, managerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        final Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(employeeService.getPaginatedEmployees(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
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
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/manager/{id}")
    public ResponseEntity<List<EmployeeProjection>> getEmployeesByManagerId(@PathVariable Long id) {
        List<EmployeeProjection> employees = employeeService.getEmployeesByManagerId(id);
        return ResponseEntity.ok(employees);
    }

}
