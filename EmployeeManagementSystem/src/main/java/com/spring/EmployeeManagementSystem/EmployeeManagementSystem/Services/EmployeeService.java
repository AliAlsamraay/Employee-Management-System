package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.DAO.EmployeeDAO;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.Attendance;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.AttendanceStatus;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Exceptions.EmailExistException;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Repositories.AttendanceRepository;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Repositories.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeDAO employeeDAO;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            EmployeeDAO employeeDAO,
            AttendanceRepository attendanceRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeDAO = employeeDAO;
        this.attendanceRepository = attendanceRepository;
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

    public void markAttendance(Long employeeId, Attendance attendance) {
        // Check if the employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        }

        // Get the employee
        Employee employee = employeeRepository.findById(employeeId).get();

        attendance.setEmployee(employee); // Set the employee
        attendance.setDate(LocalDate.now()); // Set the attendance date

        // check if the employee has already marked attendance for the day, if yes,
        // throw an exception
        if (attendanceRepository.existsByEmployeeAndDate(employee, LocalDate.now())) {
            throw new RuntimeException("Attendance already marked for today");
        }

        // Save the attendance record
        attendanceRepository.save(attendance);

    }
}
