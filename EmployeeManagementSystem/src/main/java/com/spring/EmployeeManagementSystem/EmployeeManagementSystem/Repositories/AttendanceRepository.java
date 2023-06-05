package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.Attendance;

@RepositoryRestResource(path = "attendance-records")
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}
