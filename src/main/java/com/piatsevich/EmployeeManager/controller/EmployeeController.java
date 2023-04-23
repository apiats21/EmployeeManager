package com.piatsevich.EmployeeManager.controller;

import com.piatsevich.EmployeeManager.dto.EmployeeDto;
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
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {

        return new ResponseEntity<>(EmployeeDto.fromEmployee(employeeServiceImpl.findById(id)), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        List<Employee> employeeList = employeeServiceImpl.findAll();

        if (employeeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(EmployeeDto.fromEmployees(employeeList), HttpStatus.OK);
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDto> addNewEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(EmployeeDto.fromEmployee(employeeServiceImpl.save(employee)), HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employeeToSave = EmployeeDto.toEmployee(employeeDto);

        Employee updatedEmployee = employeeServiceImpl.updateEmployee(employeeToSave);
        EmployeeDto employee = EmployeeDto.fromEmployee(updatedEmployee);

        return new ResponseEntity<>(employee, HttpStatus.OK);
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
