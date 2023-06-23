package com.spring.EmployeeManagementSystem.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.Entities.LeaveRequest;
import com.spring.EmployeeManagementSystem.Enums.LeaveRequestStatus;
import com.spring.EmployeeManagementSystem.Exceptions.AccessDeniedException;
import com.spring.EmployeeManagementSystem.Repositories.EmployeeRepository;
import com.spring.EmployeeManagementSystem.Repositories.LeaveRequestRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository,
            EmployeeService employeeService) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    public boolean isLeaveRequestDateValid(LeaveRequest leaveRequest) {
        if (leaveRequest.getStartDate() == null || leaveRequest.getEndDate() == null) {
            return false;
        }
        return leaveRequest.getStartDate().isBefore(leaveRequest.getEndDate());
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Leave Request not found"));
    }

    public List<LeaveRequest> getLeaveRequestsByEmployeeId(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    public LeaveRequest createLeaveRequest(Long employeeId, LeaveRequest leaveRequest) {
        // Check if the employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }

        // Check if the start date is before the end date
        if (!isLeaveRequestDateValid(leaveRequest)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        // Get the employee
        final Employee employee = employeeRepository.findById(employeeId).get();

        leaveRequest.setEmployee(employee); // Set the employee

        // Set leaveRequest's status to "Pending" as it is a new leave request
        leaveRequest.setStatus(LeaveRequestStatus.PENDING);

        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest updateLeaveRequest(Long managerId, LeaveRequest leaveRequestToUpdate) {

        // Check if the leave request exists
        Long leaveRequestId = leaveRequestToUpdate.getId();
        if (leaveRequestId == null) {
            throw new IllegalArgumentException("Leave Request id must be provided in the request body");
        }

        // Get the leave request
        Optional<LeaveRequest> optionalLeaveRequest = leaveRequestRepository.findById(leaveRequestId);

        if (!optionalLeaveRequest.isPresent()) {
            throw new EntityNotFoundException("Leave Request not found");
        }

        LeaveRequest leaveRequest = optionalLeaveRequest.get();

        // get the employee of the leave request
        Employee employee = leaveRequest.getEmployee();
        if (employee == null) {
            throw new EntityNotFoundException("Employee not found");
        }

        Long employeeId = employee.getId();

        // check if the manager is the manager of the employee
        if (!employeeService.hasAccessToEmployee(managerId, employeeId)) {
            throw new EntityNotFoundException("Employee not found");
        }

        // Update only the provided fields
        if (leaveRequestToUpdate.getStartDate() != null) {
            leaveRequest.setStartDate(leaveRequestToUpdate.getStartDate());
        }
        if (leaveRequestToUpdate.getEndDate() != null) {
            leaveRequest.setEndDate(leaveRequestToUpdate.getEndDate());
        }

        // Check if the start date is before the end date
        if (!isLeaveRequestDateValid(leaveRequest)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        if (leaveRequestToUpdate.getReason() != null) {
            leaveRequest.setReason(leaveRequestToUpdate.getReason());
        }
        if (leaveRequestToUpdate.getStatus() != null) {
            leaveRequest.setStatus(leaveRequestToUpdate.getStatus());
        }

        // Save the updated leaveRequest
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return updatedLeaveRequest;

    }

    public void deleteLeaveRequest(Long managerId, Long id) {
        // Check if the leave request exists
        if (!leaveRequestRepository.existsById(id)) {
            throw new EntityNotFoundException("Leave Request not found");
        }

        // Get the leave request
        LeaveRequest leaveRequest = getLeaveRequestById(id);

        // check if the manager is the manager of the employee of the leave request
        Long employeeId = leaveRequest.getEmployee().getId();

        if (!employeeService.hasAccessToEmployee(managerId, employeeId)) {
            throw new AccessDeniedException("You don't have access to this employee");
        }

        leaveRequestRepository.delete(leaveRequest);
    }
}
