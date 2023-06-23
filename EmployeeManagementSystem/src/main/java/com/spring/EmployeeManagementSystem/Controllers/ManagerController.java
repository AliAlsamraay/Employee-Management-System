package com.spring.EmployeeManagementSystem.Controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.EmployeeManagementSystem.Entities.Manager;
import com.spring.EmployeeManagementSystem.Projections.ManagerProjection;
import com.spring.EmployeeManagementSystem.Services.ManagerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping
    public ResponseEntity<Manager> createManager(@Valid @RequestBody Manager manager) {
        managerService.createManager(manager);
        return ResponseEntity.ok(manager);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<HashMap<String, String>> updateManager(@PathVariable Long id, @RequestBody Manager manager) {
        managerService.updateManager(id, manager);
        return ResponseEntity.ok(
                new HashMap<String, String>() {
                    {
                        put("message", "Manager updated successfully");
                        put("status", "success");
                        put("statusCode", "200");
                    }
                });
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HashMap<String, String>> deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Manager deleted successfully");
        response.put("status", "success");
        response.put("statusCode", "200");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<Manager>> getAllManagersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(managerService.getPaginatedManagers(pageable));
    }

    @GetMapping(value = "/with-employees")
    public ResponseEntity<List<ManagerProjection>> getAllManagers() {
        return ResponseEntity.ok(managerService.getAllManagersWithEmployees());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Long id) {
        Manager manager = managerService.getManagerById(id);
        return ResponseEntity.ok(manager);
    }
}
