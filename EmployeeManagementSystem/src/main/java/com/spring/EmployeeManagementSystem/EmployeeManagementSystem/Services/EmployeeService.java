package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.DAO.EmployeeDAO;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Exceptions.EmailExistException;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Repositories.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeDAO employeeDAO) {
        this.employeeRepository = employeeRepository;
        this.employeeDAO = employeeDAO;
    }

    public Employee saveEmployee(Employee employee) {
        // Check if the email already exists
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmailExistException("Email already exists: " + employee.getEmail());
        }

        // Save the employee
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return (Employee) employee.get();
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(Long id) {
        // Check if the employee exists
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    public Employee getEmployeeByName(String name) {
        try {
            if (!employeeRepository.existsByName(name)) {
                throw new EmployeeNotFoundException("Employee not found with name: " + name);
            }
            Employee employee = employeeDAO.getEmployeeByName(name);
            return employee;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public List<Employee> searchEmployeeByName(String name) {
        return employeeDAO.searchEmployeeByName(name);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeDAO.updateEmployee(employee);
    }
}
