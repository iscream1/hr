package com.avinty.hr;

import com.avinty.hr.dto.EmployeeDto;
import com.avinty.hr.enums.PositionEnum;
import com.avinty.hr.mapper.EmployeeMapper;
import com.avinty.hr.model.Employee;
import com.avinty.hr.repository.EmployeeRepository;
import com.avinty.hr.service.EmployeeService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class EmployeeTests {

  @MockBean
  EmployeeRepository employeeRepository;

  @Autowired
  private EmployeeMapper employeeMapper;
  private EmployeeService employeeService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    employeeService = new EmployeeService(employeeRepository, employeeMapper);
  }

	@Test
	void findByNameLike() {
    final Employee testEmployee = createTestEmployee();
    Mockito.when(employeeRepository.findByNameAndEmailLike("Jakab", null))
        .thenReturn(List.of(testEmployee));
    final List<EmployeeDto> list = employeeService.findByNameAndEmailLike("Jakab", null);
    //an employee is present
    Assertions.assertTrue(list.size() > 0);
    //found by email
    Assertions.assertEquals(testEmployee.getEmail(), list.get(0).getEmail());
  }

  @Test
  void findByNameAndEmailLike() {
    final Employee testEmployee = createTestEmployee();
    Mockito.when(employeeRepository.findByNameAndEmailLike("Jakab", "jakab@testgmail"))
        .thenReturn(List.of(testEmployee));
    final List<EmployeeDto> list = employeeService.findByNameAndEmailLike("Jakab", "jakab@testgmail");
    //an employee is present
    Assertions.assertTrue(list.size() > 0);
    //found by email
    Assertions.assertEquals(testEmployee.getEmail(), list.get(0).getEmail());
  }

  private Employee createTestEmployee() {
    final Employee employee = new Employee();
    employee.setFullName("Jakab Test");
    employee.setEmail("jakab@testgmail.com");
    employee.setPosition(PositionEnum.EMPLOYEE);
    return employee;
  }

}
