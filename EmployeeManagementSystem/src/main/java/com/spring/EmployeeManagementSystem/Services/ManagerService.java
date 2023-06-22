package com.spring.EmployeeManagementSystem.Services;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.EmployeeManagementSystem.Entities.Attendance;
import com.spring.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.Entities.Manager;
import com.spring.EmployeeManagementSystem.Exceptions.EmailExistException;
import com.spring.EmployeeManagementSystem.Projections.EmployeeProjection;
import com.spring.EmployeeManagementSystem.Projections.ManagerProjection;
import com.spring.EmployeeManagementSystem.Repositories.ManagerRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final EmployeeService employeeService;

    public ManagerService(ManagerRepository managerRepository, @Lazy EmployeeService employeeService) {
        this.managerRepository = managerRepository;
        this.employeeService = employeeService;
    }

    // get paginated managers list
    public Page<Manager> getPaginatedManagers(Pageable pageable) {
        return managerRepository.findAll(pageable);
    }

    // get all managers list without attendance of employees
    public List<ManagerProjection> getAllManagersWithEmployees() {
        return managerRepository.findAllProjectedBy();
    }

    // exists by id
    public boolean existsById(Long id) {
        return managerRepository.existsById(id);
    }

    // exists by email
    public boolean existsByEmail(String email) {
        return managerRepository.existsByEmail(email);
    }

    // get a manager by id
    public Manager getManagerById(Long id) {
        // check if the manager exists
        if (!existsById(id)) {
            throw new EntityNotFoundException("Manager not found with id: " + id);
        }
        return managerRepository.findById(id).get();
    }

    // create a manager
    public Manager createManager(Manager manager) {
        // check if the manager exists with the email
        if (managerRepository.existsByEmail(manager.getEmail())) {
            throw new EmailExistException("Manager already exists with email: " + manager.getEmail());
        }
        return managerRepository.save(manager);
    }

    // update a manager
    @Transactional
    public void updateManager(Long id, Manager manager) {
        // Check if the manager exists
        if (!existsById(id)) {
            throw new EntityNotFoundException("Manager not found with id: " + id);
        }

        // Get the manager
        Manager managerToUpdate = managerRepository.findById(id).get();

        // check if the manager exists with the email
        if (managerRepository.existsByEmail(manager.getEmail())) {
            throw new EmailExistException("Manager already exists with email: " + manager.getEmail());
        }

        // check if the manager name is not null
        if (manager.getName() == null || manager.getName().isEmpty()) {
            throw new IllegalArgumentException("Manager name cannot be null");
        }

        // check if the manager name is not the same as the current manager name
        if (manager.getName().equals(managerToUpdate.getName())) {
            throw new IllegalArgumentException("Manager name cannot be the same as the current manager name");
        }

        // check if the manager email is not null
        if (manager.getEmail() == null || manager.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Manager email cannot be null");
        }

        // check if the manager email is not the same as the current manager email
        if (manager.getEmail().equals(managerToUpdate.getEmail())) {
            throw new IllegalArgumentException("Manager email cannot be the same as the current manager email");
        }

        // check if the manager department is not null
        if (manager.getDepartment() == null || manager.getDepartment().isEmpty()) {
            throw new IllegalArgumentException("Manager department cannot be null");
        }

        // check if the manager department is not the same as the current manager
        // department
        if (manager.getDepartment().equals(managerToUpdate.getDepartment())) {
            throw new IllegalArgumentException(
                    "Manager department cannot be the same as the current manager department");
        }

        managerRepository.updateById(manager.getName(), manager.getEmail(), manager.getDepartment(), id);
    }

    // delete a manager
    public void deleteManager(Long id) {
        // Check if the manager exists
        if (!managerRepository.existsById(id)) {
            throw new EntityNotFoundException("Manager not found with id: " + id);
        }

        // Delete the manager
        managerRepository.deleteById(id);
    }

    // add employee to manager
    public void addEmployeeToManager(Long managerId, Long employeeId) {
        // Check if the manager exists
        if (!managerRepository.existsById(managerId)) {
            throw new EntityNotFoundException("Manager not found with id: " + managerId);
        }

        // Check if the employee exists
        if (!managerRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }

        // Get the manager
        Manager manager = managerRepository.findById(managerId).get();

        // Get the employee
        Employee employee = employeeService.getEmployeeById(employeeId);

        // Add the employee to the manager
        manager.addEmployee(employee);

        // Save the manager
        managerRepository.save(manager);
    }

    public List<EmployeeProjection> getEmployeesByManagerId(Long managerId) {
        return managerRepository.findEmployeesByManagerId(managerId);
    }

}
