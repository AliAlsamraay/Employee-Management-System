package com.spring.EmployeeManagementSystem.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.EmployeeManagementSystem.Entities.Attendance;
import com.spring.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.Exceptions.AttendanceMarkedException;
import com.spring.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;
import com.spring.EmployeeManagementSystem.Repositories.AttendanceRepository;
import com.spring.EmployeeManagementSystem.Repositories.EmployeeRepository;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    // get all attendances of an employee
    public List<Attendance> getAllAttendances(Long employeeId) {
        return attendanceRepository.findAllByEmployeeId(employeeId);
    }

    // get attendances of an employee by pagination
    public Page<Attendance> getPaginatedAttendances(Pageable pageable) {
        return attendanceRepository.findAll(pageable);
    }

    // mark attendance of an employee
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
            throw new AttendanceMarkedException("Attendance already marked for today");
        }

        // Save the attendance record
        attendanceRepository.save(attendance);
    }

}
