package com.piatsevich.EmployeeManager.controller;

import com.piatsevich.EmployeeManager.entity.Employee;
import com.piatsevich.EmployeeManager.service.impl.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeServiceImpl.findById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return new ResponseEntity<>(employeeServiceImpl.findAll(), HttpStatus.OK);
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeServiceImpl.save(employee), HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeServiceImpl.updateEmployee(employee), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!employeeServiceImpl.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        employeeServiceImpl.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
