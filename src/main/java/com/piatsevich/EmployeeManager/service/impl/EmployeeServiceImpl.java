package com.piatsevich.EmployeeManager.service.impl;

import com.piatsevich.EmployeeManager.entity.Employee;
import com.piatsevich.EmployeeManager.repository.EmployeeRepository;
import com.piatsevich.EmployeeManager.service.EmployeeService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Employee currentEmployee = employeeRepository.findEmployeeByName(employee.getName()).get();
        employee.setPassword(passwordEncoder.encode(currentEmployee.getPassword()));
        employee.setId(currentEmployee.getId());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee save(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);

    }

    @Override
    public boolean isExists(Long id) {
        return employeeRepository.existsById(id);
    }
}
