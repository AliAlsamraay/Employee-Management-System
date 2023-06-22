package com.spring.EmployeeManagementSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.spring.EmployeeManagementSystem.DAO.EmployeeDAO;
import com.spring.EmployeeManagementSystem.Entities.Employee;

@SpringBootApplication
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(EmployeeDAO employeeDAO) {
		// this function will run when the application starts.
		return runner -> {
			// final Long id = addEmployee(employeeDAO);
			// getEmployeeById(employeeDAO, id);
		};
	}

	public static void getEmployeeById(EmployeeDAO employeeDAO, Long id) {
		// get employee by id.
		System.out.println("Getting employee by id...");
		Employee employee = employeeDAO.getEmployeeById(id);
		System.out.println("Employee: " + employee.toString());
	}

	public static Long addEmployee(EmployeeDAO employeeDAO) {
		// create employee object.
		System.out.println("Creating employee object...");
		Employee employee = new Employee();
		employee.setName("Ahmad");
		employee.setEmail("ahmad@hotmail.com");
		employee.setDesignation("Computer Engineer");
		employee.setDepartment("Engineering");
		// save employee to database.
		System.out.println("Saving employee to database...");
		employeeDAO.saveEmployee(employee);
		System.out.println("Employee saved successfully." + employee.toString());
		return employee.getId();
	}

}
