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

    /**
     * Endpoint that returns info about certain employee
     *
     * @param id employee id
     * @return {@link EmployeeDto}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        Employee employee =  employeeServiceImpl.findById(id);

        if(employee == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        EmployeeDto employeeDto = EmployeeDto.fromEmployee(employee);

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
        List<Employee> employeeList = employeeServiceImpl.findAll();

        if (employeeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

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

        Employee result = employeeServiceImpl.save(employee);

        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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

        Employee updatedEmployee = employeeServiceImpl.updateEmployee(employeeToSave);
        EmployeeDto employee = EmployeeDto.fromEmployee(updatedEmployee);

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

        if (!employeeServiceImpl.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        employeeServiceImpl.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
