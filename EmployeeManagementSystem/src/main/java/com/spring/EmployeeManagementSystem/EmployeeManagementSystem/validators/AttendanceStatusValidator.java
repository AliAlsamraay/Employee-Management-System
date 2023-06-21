package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.validators;

import java.util.Arrays;
import java.util.List;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.AttendanceStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AttendanceStatusValidator implements
        ConstraintValidator<ValidAttendanceStatus, AttendanceStatus> {

    @Override
    public boolean isValid(AttendanceStatus statusField, ConstraintValidatorContext cxt) {
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