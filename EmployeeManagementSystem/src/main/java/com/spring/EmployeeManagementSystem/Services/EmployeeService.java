package com.spring.EmployeeManagementSystem.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.EmployeeManagementSystem.DAO.EmployeeDAO;
import com.spring.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.Entities.Manager;
import com.spring.EmployeeManagementSystem.Exceptions.AccessDeniedException;
import com.spring.EmployeeManagementSystem.Exceptions.EmailExistException;
import com.spring.EmployeeManagementSystem.Projections.EmployeeProjection;
import com.spring.EmployeeManagementSystem.Repositories.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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

    // this function to check if the manager has access to the employee or not.
    public boolean hasAccessToEmployee(Long managerId, Long employeeId) {
        // Check if the manager exists
        if (!managerService.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id: " +
                    managerId);
        }

        // Check if the employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " +
                    employeeId);
        }

        // Check if the manager has access to the employee or not
        return !employeeRepository.findAllByIdAndManagerId(employeeId, managerId).isEmpty();
    }

    public Employee saveEmployee(Employee employee, Long managerId) {
        // Check if the manager exists
        if (!managerService.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id: " +
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
            throw new EntityNotFoundException("Employee not found with id: " + id);
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

    public void deleteEmployee(Long managerId, Long employeeId) {
        // Check if the employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }

        // Check if the manager has access to the employee or not
        if (!hasAccessToEmployee(managerId, employeeId)) {
            throw new AccessDeniedException("Manager with id: " + managerId +
                    " has no access to employee with id: " + employeeId);
        }

        employeeRepository.deleteById(employeeId);
    }

    public Employee getEmployeeByName(String name) {
        try {
            if (!employeeRepository.existsByName(name)) {
                throw new EntityNotFoundException("Employee not found with name: " + name);
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

    @Transactional
    public Employee updateEmployee(Long managerId, Employee employee) {

        // Check if the employee's id is null
        if (employee.getId() == null) {
            throw new IllegalArgumentException("Employee's id cannot be null");
        }

        // Check if the employee exists
        if (!employeeRepository.existsById(employee.getId())) {
            throw new EntityNotFoundException("Employee not found with id: " + employee.getId());
        }

        // Check if the manager has access to the employee or not
        if (!hasAccessToEmployee(managerId, employee.getId())) {
            throw new AccessDeniedException("Manager with id: " + managerId +
                    " has no access to employee with id: " + employee.getId());
        }

        // check if the email already exists
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new EmailExistException("Email already exists: " + employee.getEmail());
        }

        // update the employee in the database with the new data

        // get the employee from the database
        final Employee employeeFromDB = getEmployeeById(employee.getId());

        // update the employee's data
        employeeFromDB.setName(employee.getName() == null ? employeeFromDB.getName() : employee.getName());
        employeeFromDB.setEmail(employee.getEmail() == null ? employeeFromDB.getEmail() : employee.getEmail());
        employeeFromDB.setSalary(employee.getSalary() == null ? employeeFromDB.getSalary() : employee.getSalary());
        employeeFromDB.setDepartment(
                employee.getDepartment() == null ? employeeFromDB.getDepartment() : employee.getDepartment());
        employeeFromDB.setDesignation(
                employee.getDesignation() == null ? employeeFromDB.getDesignation() : employee.getDesignation());

        // save the employee
        return employeeRepository.save(employeeFromDB);
    }

}
