package com.spring.EmployeeManagementSystem.validators;

import java.util.Arrays;
import java.util.List;

import com.spring.EmployeeManagementSystem.Enums.LeaveRequestStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LeaveRequestStatusValidator implements
        ConstraintValidator<ValidateLeaveRequestStatus, LeaveRequestStatus> {

    @Override
    public boolean isValid(LeaveRequestStatus statusField, ConstraintValidatorContext context) {
        if (statusField == null) {
            // null values are valid in this case because the @NotNull annotation is used
            return true;

        }

        List<LeaveRequestStatus> leaveRequestStatusValues = Arrays.asList(LeaveRequestStatus.values());
        // check if the value is present in the attendanceStatusValues list, if not
        // then
        // throw an exception.
        for (LeaveRequestStatus leaveRequestStatus : leaveRequestStatusValues) {
            if (leaveRequestStatus.name().equalsIgnoreCase(statusField.name())) {
                return true;
            }
        }
        return false;
    }

}