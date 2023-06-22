package com.spring.EmployeeManagementSystem.Deserializers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.spring.EmployeeManagementSystem.Entities.AttendanceStatus;
import com.spring.EmployeeManagementSystem.Exceptions.InvalidAttendanceStatusException;

public class AttendanceStatusDeserializer extends StdDeserializer<AttendanceStatus> {

    public AttendanceStatusDeserializer() {
        super(AttendanceStatus.class);
    }

    @Override
    public AttendanceStatus deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            String value = parser.getText().toUpperCase();
            List<AttendanceStatus> attendanceStatusValues = Arrays.asList(AttendanceStatus.values());
            final String message = "Invalid AttendanceStatus value: " + value + ". Valid values are: "
                    + attendanceStatusValues;
            // check if the value is present in the attendanceStatusValues list, if not then
            // throw an exception.
            for (AttendanceStatus attendanceStatus : attendanceStatusValues) {
                if (attendanceStatus.name().equalsIgnoreCase(value)) {
                    return attendanceStatus;
                }
            }

            throw new InvalidAttendanceStatusException(message);
        } catch (IllegalArgumentException e) {
            throw new InvalidAttendanceStatusException(e.toString());
        }
    }
}
