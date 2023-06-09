package com.spring.EmployeeManagementSystem.validators;

import java.util.Arrays;
import java.util.List;

import com.spring.EmployeeManagementSystem.Enums.AttendanceStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AttendanceStatusValidator implements
        ConstraintValidator<ValidateAttendanceStatus, AttendanceStatus> {

    @Override
    public boolean isValid(AttendanceStatus statusField, ConstraintValidatorContext cxt) {
        if (statusField == null) {
            // null values are valid in this case because the @NotNull annotation is used
            return true;

        }
        List<AttendanceStatus> attendanceStatusValues = Arrays.asList(AttendanceStatus.values());
        // check if the value is present in the attendanceStatusValues list, if not then
        // throw an exception.

        for (AttendanceStatus attendanceStatus : attendanceStatusValues) {
            if (attendanceStatus.name().equalsIgnoreCase(statusField.name())) {
                return true;
            }
        }
        return false;
    }

}