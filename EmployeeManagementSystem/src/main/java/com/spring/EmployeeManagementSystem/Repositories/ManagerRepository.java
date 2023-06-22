package com.spring.EmployeeManagementSystem.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.spring.EmployeeManagementSystem.Entities.Manager;
import com.spring.EmployeeManagementSystem.Projections.EmployeeProjection;
import com.spring.EmployeeManagementSystem.Projections.ManagerProjection;

@RepositoryRestResource(path = "employee-managers")
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    boolean existsByEmail(String email);

    boolean existsById(Long id);

    boolean existsByName(String name);

    @Modifying
    @Query("UPDATE Manager m set m.name = ?1, m.email = ?2, m.department = ?3 where m.id = ?4")
    void updateById(String name, String email, String department, Long id);

    @Query("SELECT e.id AS id, e.name AS name, e.email AS email, e.department AS department FROM Manager m JOIN m.employees e WHERE m.id = :managerId")
    List<EmployeeProjection> findEmployeesByManagerId(@Param("managerId") Long managerId);

    // get all managers with their employees without their attendances
    // @Query("SELECT m.id AS id, m.name AS name, m.email AS email, m.department AS
    // department, e.id AS employeeId, e.name AS employeeName, e.email AS
    // employeeEmail, e.designation AS employeeDesignation, e.department AS
    // employeeDepartment FROM Manager m JOIN m.employees e")
    List<ManagerProjection> findAllProjectedBy();

}
