package com.spring.EmployeeManagementSystem.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.EmployeeManagementSystem.Entities.Attendance;
import com.spring.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.Entities.LeaveRequest;
import com.spring.EmployeeManagementSystem.Entities.PerformanceEvaluation;
import com.spring.EmployeeManagementSystem.Enums.AttendanceStatus;
import com.spring.EmployeeManagementSystem.Enums.LeaveRequestStatus;
import com.spring.EmployeeManagementSystem.Repositories.PerformanceEvaluationRepository;

@Service
public class PerformanceEvaluationService {
    private final PerformanceEvaluationRepository evaluationRepository;
    private final EmployeeService employeeService;
    private final AttendanceService attendanceService;
    private final LeaveRequestService leaveRequestService;

    public PerformanceEvaluationService(PerformanceEvaluationRepository evaluationRepository,
            EmployeeService employeeService, AttendanceService attendanceService,
            LeaveRequestService leaveRequestService) {
        this.evaluationRepository = evaluationRepository;
        this.employeeService = employeeService;
        this.attendanceService = attendanceService;
        this.leaveRequestService = leaveRequestService;
    }

    public PerformanceEvaluation createEvaluation(
            Long managerId,
            Long employeeId,
            PerformanceEvaluation evaluation) {
        // check if the manager has access to the employee
        if (!employeeService.hasAccessToEmployee(managerId, employeeId)) {
            throw new RuntimeException("Manager does not have access to the employee");
        }

        // check if the employee exists in the database.
        Employee employee = employeeService.getEmployeeById(employeeId);
        evaluation.setEmployee(employee);

        // Save the evaluation
        return evaluationRepository.save(evaluation);
    }

    public List<PerformanceEvaluation> getEvaluationsByEmployeeId(Long employeeId) {
        // Retrieve evaluations for a specific employee
        return evaluationRepository.findByEmployeeId(employeeId);
    }

    public PerformanceEvaluation calculatePerformanceEvaluation(Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);

        // Perform calculations based on performance criteria
        double expectedSalary = calculateExpectedSalary(employee);
        double rating = estimateRating(employee);

        // Create a new performance evaluation object
        PerformanceEvaluation performanceEvaluation = new PerformanceEvaluation();
        performanceEvaluation.setId(0L);
        performanceEvaluation.setEmployee(employee);
        performanceEvaluation.setEvaluationDate(LocalDate.now());
        performanceEvaluation.setExpectedSalary(expectedSalary);
        performanceEvaluation.setRating(rating);

        return performanceEvaluation;
    }

    private double getAttendancePercentage(Employee employee) {
        // Get the employee's attendance records
        List<Attendance> attendanceList = attendanceService.getAllAttendances(employee.getId());

        // Calculate the total working days and days present
        int totalWorkingDays = attendanceList.size();

        // check if the employee has any attendance records
        if (totalWorkingDays == 0) {
            throw new IllegalArgumentException("Employee has no attendance records");
        }

        int daysPresent = 0;
        for (Attendance attendance : attendanceList) {
            if (attendance.getStatus() == AttendanceStatus.PRESENT) {
                daysPresent++;
            }
        }

        // Calculate the attendance percentage
        return (double) daysPresent / totalWorkingDays;
    }

    private double calculateExpectedSalary(Employee employee) {
        // Calculate the attendance percentage
        double attendancePercentage = getAttendancePercentage(employee);

        // Get the employee's leave requests
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByEmployeeId(employee.getId());

        // Calculate the number of approved leave requests
        long approvedLeaveRequests = leaveRequests.stream()
                .filter(request -> request.getStatus() == LeaveRequestStatus.APPROVED)
                .count();

        // Calculate the expected salary based on attendance and leave requests
        double expectedSalary = employee.getSalary();
        if (attendancePercentage < 0.8) {
            expectedSalary -= 0.1 * employee.getSalary(); // Deduct 10% for low attendance
        }
        if (approvedLeaveRequests > 5) {
            expectedSalary -= 0.05 * employee.getSalary(); // Deduct 5% for excessive leave requests
        }

        return expectedSalary;
    }

    private double estimateRating(Employee employee) {
        // Get the employee's attendance percentage
        double attendancePercentage = getAttendancePercentage(employee);

        // Get the employee's performance evaluations
        List<PerformanceEvaluation> evaluations = evaluationRepository.findByEmployeeId(employee.getId());

        // Calculate the average rating from previous evaluations
        double averageRating = evaluations.stream()
                .mapToDouble(PerformanceEvaluation::getRating)
                .average()
                .orElse(0.0);

        // Calculate the estimated rating based on attendance and average rating
        double estimatedRating = averageRating;
        if (attendancePercentage >= 0.9) {
            estimatedRating += 0.1; // Increase rating by 0.1 for high attendance
        } else if (attendancePercentage < 0.8) {
            estimatedRating -= 0.1; // Decrease rating by 0.1 for low attendance
        }

        return estimatedRating;
    }
}
