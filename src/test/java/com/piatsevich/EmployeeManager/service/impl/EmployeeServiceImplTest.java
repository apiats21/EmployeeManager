package com.piatsevich.EmployeeManager.service.impl;

import com.piatsevich.EmployeeManager.entity.Employee;
import com.piatsevich.EmployeeManager.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.util.Lists.list;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(MockitoJUnitRunner.class)
class EmployeeServiceImplTest {

    private MockMvc mockMvc;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    Employee empl1 = Employee
            .builder()
            .id(1L)
            .name("firstUser")
            .email("testUser@gmail.com")
            .password("pass1")
            .build();

    Employee empl2 = Employee
            .builder()
            .id(2L)
            .name("secondUser")
            .email("secondUser@gmail.com")
            .password("pass2")
            .build();

    List<Employee> employeeList = list(empl1, empl2);


    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeService).build();
    }

    @Test
    @DisplayName("If employee request employeeList return all employees")
    void findAll() {
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> employees = employeeService.findAll();

        assertEquals(2, employees.size());
        assertEquals(employeeList.get(0).getName(), employees.get(0).getName());
        assertEquals(employeeList.get(1).getName(), employees.get(1).getName());
    }

    @Test
    @DisplayName("If employee request employeeByID return employee by Id")
    void findById() {
        when(employeeRepository.findById(empl1.getId())).thenReturn(Optional.ofNullable(empl1));
        Employee employee = employeeService.findById(1L);

        assertEquals(employeeList.get(0).getName(), employee.getName());
    }

    @Test
    @DisplayName("If admin request delete employee confirm that employee was deleted")
    void deleteById() {
        employeeService.deleteById(1L);

        verify(employeeRepository, times(1)).deleteById(empl1.getId());
    }

    @Test
    @DisplayName("If admin request that employee exist return boolean")
    void isExists() {
        when(employeeRepository.existsById(empl1.getId())).thenReturn(true);
        Boolean isExistById = employeeService.isExists(1L);

        assertEquals(true, isExistById);
    }
}