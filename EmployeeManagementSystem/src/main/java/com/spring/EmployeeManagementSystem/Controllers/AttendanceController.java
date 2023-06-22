package com.spring.EmployeeManagementSystem.Controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import com.spring.EmployeeManagementSystem.Entities.Attendance;
import com.spring.EmployeeManagementSystem.Services.AttendanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<HashMap<String, String>> markAttendance(@PathVariable Long id,
            @Valid @RequestBody Attendance attendance) {
        attendanceService.markAttendance(id, attendance);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Attendance marked successfully");
        response.put("status", "success");
        response.put("statusCode", "200");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Attendance>> getEmployeeAttendanceById(@PathVariable Long id) {
        List<Attendance> attendance = attendanceService.getAllAttendances(id);
        return ResponseEntity.ok(attendance);
    }

    @GetMapping
    public Page<Attendance> getPaginatedAttendances(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return attendanceService.getPaginatedAttendances(pageable);
    }

}
