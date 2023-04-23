package com.piatsevich.EmployeeManager.controller;

import com.piatsevich.EmployeeManager.dto.EmployeeDto;
import com.piatsevich.EmployeeManager.entity.Employee;
import com.piatsevich.EmployeeManager.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.util.Lists.list;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(MockitoJUnitRunner.class)
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    EmployeeServiceImpl employeeService;

    @InjectMocks
    EmployeeController employeeController;

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

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    @DisplayName("If employee request employeeById return employee by Id")
    void getEmployeeById() {
        when(employeeService.findById(empl1.getId())).thenReturn(empl1);
        ResponseEntity<EmployeeDto> response = employeeController.getEmployeeById(empl1.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empl1.getName(), Objects.requireNonNull(response.getBody()).getName());
        assertEquals(empl1.getEmail(), response.getBody().getEmail());
    }

    @Test
    @DisplayName("If employee request employeeById that does not exist then return null")
    void getByIdFail() {
        when(employeeService.findById(empl1.getId())).thenReturn(null);
        ResponseEntity<EmployeeDto> response = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("If employee request employeeList return all employees")
    void getAllEmployee() {
        List<Employee> employeeList = list(empl1, empl2);

        when(employeeService.findAll()).thenReturn(employeeList);

        ResponseEntity<List<EmployeeDto>> response = employeeController.getAllEmployee();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeList.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(empl1.getName(), response.getBody().get(0).getName());
        assertEquals(empl2.getName(), response.getBody().get(1).getName());
    }

    @Test
    @DisplayName("If admin request to delete employee return than verify that employee was deleted")
    void deleteById() {
        when(employeeService.isExists(empl1.getId())).thenReturn(true);

        ResponseEntity<Void> response = employeeController.deleteById(empl1.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(employeeService, times(1)).deleteById(empl1.getId());
    }

    @Test
    @DisplayName("If admin request to delete employee that does not exist than return BAD_REQUEST status")
    void deleteByIdFail() {
        when(employeeService.isExists(anyLong())).thenReturn(false);
        ResponseEntity<Void> response = employeeController.deleteById(anyLong());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}