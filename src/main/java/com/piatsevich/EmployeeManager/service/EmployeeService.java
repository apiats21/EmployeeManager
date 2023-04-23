package com.piatsevich.EmployeeManager.service;

import com.piatsevich.EmployeeManager.entity.Employee;

/**
 * Service that works with {@link Employee}.
 */
public interface EmployeeService extends GenericService<Employee, Long>{
    Employee updateEmployee(Employee employee);
}
