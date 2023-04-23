package com.piatsevich.EmployeeManager.dto;

import com.piatsevich.EmployeeManager.entity.Employee;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class EmployeeDto {
    private String name;
    private String email;

    public static EmployeeDto fromEmployee(Employee employee) {
        return EmployeeDto.builder()
                .name(employee.getName())
                .email(employee.getEmail())
                .build();
    }

    public static Employee toEmployee(EmployeeDto employeeDto) {
        return Employee.builder()
                .name(employeeDto.getName())
                .email(employeeDto.getEmail())
                .build();
    }

    public static List<EmployeeDto> fromEmployees(List<Employee> employees) {

        List<EmployeeDto> result = new ArrayList<>();
        employees.forEach(employee -> result.add(EmployeeDto.fromEmployee(employee)));
        return result;
    }
}
