package com.piatsevich.EmployeeManager.service.impl;

import com.piatsevich.EmployeeManager.entity.Employee;
import com.piatsevich.EmployeeManager.repository.EmployeeRepository;
import com.piatsevich.EmployeeManager.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link EmployeeService}
 * Works with {@link EmployeeRepository}
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     * For updating employee using method {@link EmployeeRepository#save(Object)}
     *
     * @param employee employee
     * @return {@link Employee}
     */
    @Override
    public Employee updateEmployee(Employee employee) {
        log.info("Updating employee in DB: {}", employee.getName());
        Employee currentEmployee = employeeRepository.findEmployeeByName(employee.getName()).get();
        employee.setPassword(passwordEncoder.encode(currentEmployee.getPassword()));
        employee.setId(currentEmployee.getId());
        log.info("Employee updated: {}", employee.getName());
        return employeeRepository.save(employee);
    }

    /**
     * {@inheritDoc}
     * For saving employee using method {@link EmployeeRepository#save(Object)}
     *
     * @param employee employee from {@link Employee}
     * @return {@link Employee}
     */
    @Override
    public Employee save(Employee employee) {
        log.info("Checking inf employee is exist in DB with name: {}", employee.getName());
        Employee currentEmployee = employeeRepository.findEmployeeByName(employee.getName()).get();
        if (currentEmployee.getName() != null) {
            return null;
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        log.info("Saving employee in DB with name: {}", employee.getName());
        return employeeRepository.save(employee);
    }

    /**
     * For getting employees using method {@link EmployeeRepository#findAll()}
     *
     * @return list of client
     */
    @Override
    public List<Employee> findAll() {
        log.info("Returning list of employee from DB");
        return employeeRepository.findAll();
    }

    /**
     * For getting employee by id using method {@link EmployeeRepository#findById(Object)}
     *
     * @param id clients id
     * @return {@link Employee}
     */
    @Override
    public Employee findById(Long id) {
        log.info("Requesting employee with id: {} from DB", id);
        return employeeRepository.findById(id).get();
    }

    /**
     * For deleting employee by id using method {@link EmployeeRepository#deleteById(Object)}
     *
     * @param id employee id
     */
    @Override
    public void deleteById(Long id) {
        log.info("Deleting employee with id: {} from DB", id);
        employeeRepository.deleteById(id);

    }

    /**
     * For checking if employee exsist by id using method{@link EmployeeRepository#existsById(Object)}
     *
     * @param id employee id
     * @return boolean if exist
     */
    @Override
    public boolean isExists(Long id) {
        log.info("Checking if employee with id: {} exist in DB", id);
        return employeeRepository.existsById(id);
    }
}
