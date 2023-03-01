package com.avinty.hr.service;

import com.avinty.hr.dto.EmployeeDto;
import com.avinty.hr.mapper.EmployeeMapper;
import com.avinty.hr.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;

  //find an employee with name and/or email filtering (contains)
  public List<EmployeeDto> findByNameAndEmailLike(final String name, final String email) {
    return employeeMapper.toDto(employeeRepository.findByNameAndEmailLike(name, email));
  }
}
