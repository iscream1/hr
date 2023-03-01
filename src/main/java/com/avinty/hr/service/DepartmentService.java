package com.avinty.hr.service;

import com.avinty.hr.dto.DepartmentDto;
import com.avinty.hr.exception.HrException;
import com.avinty.hr.mapper.DepartmentMapper;
import com.avinty.hr.model.Department;
import com.avinty.hr.model.Employee;
import com.avinty.hr.repository.DepartmentRepository;
import com.avinty.hr.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

  private static final String DEP_NOT_FOUND = "Given department not found in the database.";
  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;
  private final DepartmentMapper departmentMapper;

  //filter departments by name (contains)
  public List<DepartmentDto> findByNameLike(String name) {
    return departmentMapper.toDto(departmentRepository.findByNameLike(name));
  }

  //list all departments with their employees
  @Transactional
  public List<DepartmentDto> findAll() {
    return departmentMapper.toDto(departmentRepository.findAll(Sort.by(Order.asc("name"))));
  }

  //set manager of a specific department
  //an employee is only allowed to be set as a manager if its position is MANAGER
  @Transactional
  public void setManager(Long departmentId, Long employeeId) {
    final Optional<Department> department = departmentRepository.findById(departmentId);
    if (department.isPresent()) {
      final Department departmentEntity = department.get();
      departmentEntity.setManagerId(employeeId);
      departmentRepository.save(departmentEntity);
    } else {
      throw new HrException(DEP_NOT_FOUND);
    }
  }

  //delete department by ID
  @Transactional
  public void delete(Long departmentId) {
    final Optional<Department> department = departmentRepository.findById(departmentId);
    if (department.isPresent()) {
      final Department departmentEntity = department.get();
      final Set<Employee> employeeList = departmentEntity.getEmployeeList();
      employeeList.forEach(employee -> employee.setDepartmentId(null));
      employeeRepository.saveAll(employeeList);
      departmentRepository.delete(departmentEntity);
    } else {
      throw new HrException(DEP_NOT_FOUND);
    }
  }
}
