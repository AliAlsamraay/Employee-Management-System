package com.spring.EmployeeManagementSystem.EmployeeManagementSystem.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Entities.Employee;
import com.spring.EmployeeManagementSystem.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    private final EntityManager entityManager;

    @Autowired
    public EmployeeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // we need to start and end transaction manually in spring boot if we are not
    // using spring data jpa.
    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        // save employee to database.
        entityManager.persist(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        // if employee not found then it will throw EmployeeNotFoundException.
        final String queryString = "from Employee where id = :id";
        Employee employee = entityManager.createQuery(queryString, Employee.class).setParameter("id", id)
                .getSingleResult();
        if (employee != null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }

        // get employee from database using primary key/id.
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        // get all employees from database.
        List<Employee> employees = entityManager.createQuery("from Employee", Employee.class).getResultList();
        return employees;
    }

    @Override
    public Employee getEmployeeByName(String name) {
        List<Employee> employee = entityManager
                .createQuery("from Employee where name = :name", Employee.class)
                .setParameter("name", name)
                .getResultList();
        if (!employee.isEmpty()) {
            return employee.get(0);
        }
        throw new EmployeeNotFoundException("Employee not found with name: " + name);
    }

    @Override
    public List<Employee> searchEmployeeByName(String name) {
        final String queryString = "from Employee where name like concat('%', :name, '%')";
        List<Employee> employees = entityManager.createQuery(queryString, Employee.class)
                .setParameter("name", name).getResultList();
        return employees;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        // update employee in database.
        Employee updatedEmployee = entityManager.merge(employee);
        return updatedEmployee;
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        // delete employee from database using primary key/id.
        // final String queryString = "DELETE FROM Employee WHERE id = :id";
        // entityManager.createQuery(queryString, Employee.class)
        // .setParameter("id", id).executeUpdate();

        Employee employee = entityManager.find(Employee.class, id);
        entityManager.remove(employee);
    }

    @Override
    public void deleteAllEmployees() {
        // delete all employees from database.
        final String queryString = "DELETE FROM Employee";
        entityManager.createQuery(queryString, Employee.class).executeUpdate();
    }

}