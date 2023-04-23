package com.piatsevich.EmployeeManager.repository;

import com.piatsevich.EmployeeManager.dto.EmployeeDto;
import com.piatsevich.EmployeeManager.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeByName(String name);
}
