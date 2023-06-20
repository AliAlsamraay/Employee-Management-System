package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.validators;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.AttendanceStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AttendanceStatusValidator implements
        ConstraintValidator<AttendanceStatusConstraint, String> {

    @Override
    public void initialize(AttendanceStatusConstraint attendanceStatusConstraint) {

    }

    @Override
    public boolean isValid(String statusField, ConstraintValidatorContext cxt) {
        if (statusField == null) {
            return false;
        }

        // convert the AttendanceStatus enum values to list and check if the statusField
        // is present in the list
        final List<String> attendanceStatusList = Arrays.asList(AttendanceStatus.values()).stream().map(Enum::name)
                .collect(Collectors.toList());

        if (!attendanceStatusList.contains(statusField)) {
            // if the statusField is not present in the list then return false
            return false;
        }

        return true;

    }

}