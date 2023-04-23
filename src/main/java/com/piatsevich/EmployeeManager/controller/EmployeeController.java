package com.piatsevich.EmployeeManager.controller;

import com.piatsevich.EmployeeManager.dto.EmployeeDto;
import com.piatsevich.EmployeeManager.entity.Employee;
import com.piatsevich.EmployeeManager.service.impl.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    /**
     * Endpoint that returns info about certain employee
     *
     * @param id employee id
     * @return {@link EmployeeDto}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        log.info("Request for employee with employee id: {}", id);
        Employee employee = employeeServiceImpl.findById(id);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        EmployeeDto employeeDto = EmployeeDto.fromEmployee(employee);
        log.info("Returning employee with name: {}", employeeDto.getName());
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * Endpoint that returns employee list
     *
     * @return list of employees
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        log.info("Request for employee list");
        List<Employee> employeeList = employeeServiceImpl.findAll();
        log.info("Cannot find employee");
        if (employeeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Returning employee list with size: {}", employeeList.size());
        return new ResponseEntity<>(EmployeeDto.fromEmployees(employeeList), HttpStatus.OK);
    }

    /**
     * Endpoint that creates an employee
     *
     * @param employee employee
     * @return {@link EmployeeDto}
     */
    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDto> addNewEmployee(@RequestBody Employee employee) {
        log.info("Request for adding new employee with employee name: {}", employee.getName());
        Employee result = employeeServiceImpl.save(employee);
        log.info("Cannot add new employee with name: {}", employee.getName());
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Returning new employee that was added with name: {}", employee.getName());
        return new ResponseEntity<>(EmployeeDto.fromEmployee(result), HttpStatus.OK);
    }

    /**
     * Endpoint that updates employee
     *
     * @param employeeDto employee
     * @return {@link EmployeeDto}
     */
    @PutMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employeeToSave = EmployeeDto.toEmployee(employeeDto);
        log.info("Request for updating an employee with name: {}", employeeDto.getName());
        Employee updatedEmployee = employeeServiceImpl.updateEmployee(employeeToSave);
        EmployeeDto employee = EmployeeDto.fromEmployee(updatedEmployee);
        log.info("Returning employee that was updated with name: {}", employee.getName());
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    /**
     * Endpoint that deletes employee
     *
     * @param id employee id
     * @return httpStatus
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Request for deleting employee with id: {}", id);
        if (!employeeServiceImpl.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Returning status that employee was successful deleted");
        employeeServiceImpl.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
