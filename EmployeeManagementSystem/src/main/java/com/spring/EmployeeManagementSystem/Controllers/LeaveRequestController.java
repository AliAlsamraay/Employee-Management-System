package com.spring.EmployeeManagementSystem.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.EmployeeManagementSystem.Entities.LeaveRequest;
import com.spring.EmployeeManagementSystem.Services.LeaveRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @GetMapping("/{id}")
    public LeaveRequest getLeaveRequestById(@PathVariable("id") Long id) {
        return leaveRequestService.getLeaveRequestById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getLeaveRequestsByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        return leaveRequestService.getLeaveRequestsByEmployeeId(employeeId);
    }

    @PostMapping
    public LeaveRequest createLeaveRequest(
            @RequestParam(name = "employeeId", required = true) Long employeeId,
            @Valid @RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.createLeaveRequest(employeeId, leaveRequest);
    }

    @PutMapping
    public LeaveRequest updateLeaveRequest(
            @RequestParam(name = "managerId", required = true) Long managerId,
            @RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.updateLeaveRequest(managerId, leaveRequest);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLeaveRequest(
            @RequestParam(name = "managerId", required = true) Long managerId,
            @RequestParam(name = "leaveRequestId", required = true) Long id) {
        leaveRequestService.deleteLeaveRequest(managerId, id);
        return ResponseEntity.ok("Leave request deleted successfully");
    }
}
