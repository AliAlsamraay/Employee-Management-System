CREATE DATABASE  IF NOT EXISTS `employee_management`;
USE `employee_management`;

DROP TABLE IF EXISTS `manager`, `employee`, `attendance`, `leave_request`, `performance_evaluations`, `authorities`, `users`;

-- Create the users table
CREATE TABLE `users` (
    `username` VARCHAR(50) NOT NULL,
    `password`  VARCHAR(68) NOT NULL,
    `enabled` TINYINT NOT NULL,
    PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1; 

-- insert initial data into users table.
-- the password is 'abc123' for all users.
insert into `users` values 
('ali','{bcrypt}$2a$12$2lhISNX/0kMOM3EOZTgwVeyA7euZh9Z8EfiW3hQNGE0U16R9vb/6K',1),
('ahmad','{bcrypt}$2a$12$A7zeB9qbw6T/2q6PgRKpxeROpjWAxYMd5WgjcT7JnNySRA38VF0Vq',1),
('mohammad','{bcrypt}$2a$12$qg9wHqjKJs04jLoy216TxugmHiY2dWTNVfm6uHpA/A3QKT99XRsPu',1);

-- Create the authorities table
CREATE TABLE `authorities` (
    `username` VARCHAR(50) NOT NULL,
    `authority` VARCHAR(50) NOT NULL,
    UNIQUE KEY `authorities5_idx_1` (`username`,`authority`),
    CONSTRAINT `authorities5_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- insert initial data into roles table.
insert into `authorities` values 
('ali','ROLE_ADMIN'),
('ali','ROLE_MANAGER'),
('ali','ROLE_EMPLOYEE'),
('ahmad','ROLE_MANAGER'),
('mohammad','ROLE_EMPLOYEE');

-- Create the Manager table
CREATE TABLE `manager` (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email  VARCHAR(100) UNIQUE,
    department VARCHAR(100)
);

-- Create the Employee table 
CREATE TABLE `employee` (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	email  VARCHAR(100) UNIQUE,
	designation VARCHAR(100),
	department VARCHAR(100),
    salary INTEGER,
    manager_id INTEGER NOT NULL,
    FOREIGN KEY (manager_id) REFERENCES manager (id)
);

-- Create the Attendance table
CREATE TABLE `attendance` (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    employee_id INTEGER NOT NULL,
    date DATE,
    status ENUM(
    'PRESENT', 'ABSENT', 'LATE', 
    'EXCUSED', 'VACATION','SICK_LEAVE',
    'WORK_FROM_HOME','BUSINESS_TRIP','OUT_OF_OFFICE','ON_LEAVE'
    ) NOT NULL,
    
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);

-- Create the LeaveRequest table
CREATE TABLE `leave_request` (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    employee_id INTEGER,
    start_date DATE,
    end_date DATE,
    reason VARCHAR(200),
    status ENUM(
    'PENDING', 'APPROVED', 'REJECTED', 
    'CANCELLED','COMPLETED'
    ) DEFAULT 'PENDING',
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);


-- create the performance_evaluations table
CREATE TABLE `performance_evaluations` (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    employee_id INTEGER,
    evaluation_date DATE,
    rating DECIMAL(2,1),
    expected_salary INTEGER,
    comment VARCHAR(200),
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);
