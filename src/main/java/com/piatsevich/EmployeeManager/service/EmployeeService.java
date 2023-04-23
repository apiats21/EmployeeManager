package com.piatsevich.EmployeeManager.service;

import com.piatsevich.EmployeeManager.dto.EmployeeDto;
import com.piatsevich.EmployeeManager.entity.Employee;

public interface EmployeeService extends GenericService<Employee, Long>{
    Employee updateEmployee(Employee employee);
}
