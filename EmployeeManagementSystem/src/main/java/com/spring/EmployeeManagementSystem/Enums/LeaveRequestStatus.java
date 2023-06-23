package com.spring.EmployeeManagementSystem.Enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public enum LeaveRequestStatus {
    PENDING,
    APPROVED,
    REJECTED,
    CANCELLED,
    COMPLETED
}
