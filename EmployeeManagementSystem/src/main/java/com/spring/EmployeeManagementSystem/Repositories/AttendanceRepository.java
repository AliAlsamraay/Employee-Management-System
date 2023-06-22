package com.spring.EmployeeManagementSystem.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.spring.EmployeeManagementSystem.Entities.Attendance;
import com.spring.EmployeeManagementSystem.Entities.Employee;

@RepositoryRestResource(path = "attendance-records")
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByEmployeeAndDate(Employee employee, LocalDate date);

    List<Attendance> findAllByEmployeeId(Long employeeId);
}
