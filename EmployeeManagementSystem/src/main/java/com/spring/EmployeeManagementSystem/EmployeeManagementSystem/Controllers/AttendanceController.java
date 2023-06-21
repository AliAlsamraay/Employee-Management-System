package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.Attendance;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Services.AttendanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> markAttendance(@PathVariable Long id, @Valid @RequestBody Attendance attendance) {
        attendanceService.markAttendance(id, attendance);
        return ResponseEntity.ok("Attendance marked successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Attendance>> getEmployeeAttendanceById(@PathVariable Long id) {
        List<Attendance> attendance = attendanceService.getAllAttendances(id);
        return ResponseEntity.ok(attendance);
    }

}
