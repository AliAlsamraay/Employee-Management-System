CREATE DATABASE  IF NOT EXISTS `employee_management`;
USE `employee_management`;

DROP TABLE IF EXISTS `Employee`, `Attendance`, `LeaveRequest`;

-- Create the Employee table 
CREATE TABLE Employee (
	id INTEGER PRIMARY KEY,
	name VARCHAR(100),
	email VARCHAR(100),
	designation VARCHAR(100),
	department VARCHAR(100)
);

-- Create the Attendance table
CREATE TABLE Attendance (
    id INTEGER PRIMARY KEY,
    employee_id INTEGER,
    date DATE,
    status VARCHAR(20),
    FOREIGN KEY (employee_id) REFERENCES Employee (id)
);

-- Create the LeaveRequest table
CREATE TABLE LeaveRequest (
    id INTEGER PRIMARY KEY,
    employee_id INTEGER,
    start_date DATE,
    end_date DATE,
    reason VARCHAR(200),
    status VARCHAR(20),
    FOREIGN KEY (employee_id) REFERENCES Employee (id)
);
