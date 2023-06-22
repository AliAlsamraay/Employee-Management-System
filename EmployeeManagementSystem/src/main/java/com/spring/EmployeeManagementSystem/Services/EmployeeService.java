package com.spring.EmployeeManagementSystem.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.EmployeeManagementSystem.DAO.EmployeeDAO;
import com.spring.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.Entities.Manager;
import com.spring.EmployeeManagementSystem.Exceptions.EmailExistException;
import com.spring.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;
import com.spring.EmployeeManagementSystem.Projections.EmployeeProjection;
import com.spring.EmployeeManagementSystem.Repositories.EmployeeRepository;

import static java.lang.Math.toIntExact;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeDAO employeeDAO;
    private final ManagerService managerService;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            EmployeeDAO employeeDAO,
            ManagerService managerService) {
        this.employeeRepository = employeeRepository;
        this.employeeDAO = employeeDAO;
        this.managerService = managerService;
    }

    public Employee saveEmployee(Employee employee, Long managerId) {
        // Check if the manager exists
        if (!managerService.existsById(managerId)) {
            throw new EmployeeNotFoundException("Manager not found with id: " +
                    managerId);
        }

        // Set the manager
        Manager manager = managerService.getManagerById(managerId);

        // Set the manager
        employee.setManager(manager);

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

    // get paginated employees list
    public Page<Employee> getPaginatedEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    // get all employees by manager id
    public List<EmployeeProjection> getEmployeesByManagerId(Long managerId) {
        return employeeRepository.findByManagerId(managerId);
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
