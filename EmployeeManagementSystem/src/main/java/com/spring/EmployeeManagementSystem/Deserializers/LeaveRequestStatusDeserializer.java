package com.spring.EmployeeManagementSystem.Deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.util.Arrays;
import java.util.List;

import com.spring.EmployeeManagementSystem.Enums.LeaveRequestStatus;
import com.spring.EmployeeManagementSystem.Exceptions.InvalidLeaveRequestStatusException;
import com.fasterxml.jackson.core.JsonParser;

public class LeaveRequestStatusDeserializer extends StdDeserializer<LeaveRequestStatus> {

    public LeaveRequestStatusDeserializer() {
        super(LeaveRequestStatus.class);
    }

    @Override
    public LeaveRequestStatus deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            String value = parser.getText().toUpperCase();
            List<LeaveRequestStatus> leaveRequestStatus = Arrays.asList(LeaveRequestStatus.values());
            final String message = "Invalid Attendance Status value: " + value + ". Valid values are: "
                    + leaveRequestStatus;
            // check if the value is present in the attendanceStatusValues list, if not then
            // throw an exception.
            for (LeaveRequestStatus statusValue : leaveRequestStatus) {
                if (statusValue.name().equalsIgnoreCase(value)) {
                    return statusValue;
                }
            }

            throw new InvalidLeaveRequestStatusException(message);
        } catch (IllegalArgumentException e) {
            throw new InvalidLeaveRequestStatusException(e.toString());
        }
    }
}
