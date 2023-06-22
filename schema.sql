CREATE DATABASE  IF NOT EXISTS `employee_management`;
USE `employee_management`;

DROP TABLE IF EXISTS `Manager`, `Employee`, `Attendance`, `LeaveRequest`, `authorities`, `users`;

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
CREATE TABLE `Manager` (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email  VARCHAR(100) UNIQUE,
    department VARCHAR(100)
);

-- Create the Employee table 
CREATE TABLE `Employee` (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	email  VARCHAR(100) UNIQUE,
	designation VARCHAR(100),
	department VARCHAR(100),
    salary INTEGER,
    manager_id INTEGER NOT NULL,
    FOREIGN KEY (manager_id) REFERENCES Manager (id)
);

-- Create the Attendance table
CREATE TABLE `Attendance` (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    employee_id INTEGER NOT NULL,
    date DATE,
    status ENUM(
    'PRESENT', 'ABSENT', 'LATE', 
    'EXCUSED', 'VACATION','SICK_LEAVE',
    'WORK_FROM_HOME','BUSINESS_TRIP','OUT_OF_OFFICE','ON_LEAVE'
    ) NOT NULL,
    
    FOREIGN KEY (employee_id) REFERENCES Employee (id)
);

-- Create the LeaveRequest table
CREATE TABLE `LeaveRequest` (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    employee_id INTEGER,
    start_date DATE,
    end_date DATE,
    reason VARCHAR(200),
    status VARCHAR(20),
    FOREIGN KEY (employee_id) REFERENCES Employee (id)
);
