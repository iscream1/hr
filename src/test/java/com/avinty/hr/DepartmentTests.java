package com.avinty.hr;

import com.avinty.hr.dto.DepartmentDto;
import com.avinty.hr.enums.PositionEnum;
import com.avinty.hr.exception.HrException;
import com.avinty.hr.mapper.DepartmentMapper;
import com.avinty.hr.model.Department;
import com.avinty.hr.model.Employee;
import com.avinty.hr.repository.DepartmentRepository;
import com.avinty.hr.repository.EmployeeRepository;
import com.avinty.hr.service.DepartmentService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class DepartmentTests {

  @MockBean
  DepartmentRepository departmentRepository;
  @MockBean
  EmployeeRepository employeeRepository;

  @Autowired
  private DepartmentMapper departmentMapper;
  private DepartmentService departmentService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    departmentService = new DepartmentService(departmentRepository, employeeRepository, departmentMapper);
  }

  @Test
  void findByNameLike() {
    final Department testDepartment = createTestDepartment();
    Mockito.when(departmentRepository.findByNameLike("Test Department"))
        .thenReturn(List.of(testDepartment));
    final List<DepartmentDto> list = departmentService.findByNameLike("Test Department");
    //a department is present
    Assertions.assertTrue(list.size() > 0);
    //found by ID
    Assertions.assertEquals(testDepartment.getId(), list.get(0).getId());
    //mapped list is not null
    Assertions.assertNotNull(list.get(0).getEmployeeList());
    //mapped employees are present
    Assertions.assertEquals(list.get(0).getEmployeeList().size(), 2);
  }

  @Test
  void setManager() {
    final Department testDepartment = createTestDepartment();
    Mockito.when(departmentRepository.findById(0L)).thenReturn(Optional.of(testDepartment));
    Mockito.when(employeeRepository.findById(0L)).thenReturn(Optional.of(createTestEmployee()));
    Mockito.when(employeeRepository.findById(0L)).thenReturn(Optional.of(createTestManager()));
    //no department found with ID
    Assertions.assertThrows(HrException.class,
        () -> departmentService.setManager(1L, 1L));
  }

  private Department createTestDepartment() {
    final Department department = new Department();
    department.setId(0L);
    department.setName("Test Department");
    department.setEmployeeList(new HashSet<>(List.of(createTestEmployee(), createTestManager())));
    return department;
  }

  private Employee createTestEmployee() {
    final Employee employee = new Employee();
    employee.setId(0L);
    employee.setFullName("Jakab Test");
    employee.setEmail("jakab@testgmail.com");
    employee.setPosition(PositionEnum.EMPLOYEE);
    employee.setDepartmentId(0L);
    return employee;
  }

  private Employee createTestManager() {
    final Employee employee = new Employee();
    employee.setId(1L);
    employee.setFullName("Manager Test");
    employee.setEmail("manager@testgmail.com");
    employee.setPosition(PositionEnum.MANAGER);
    employee.setDepartmentId(0L);
    return employee;
  }

}
