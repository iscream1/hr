package com.avinty.hr.mapper;

import com.avinty.hr.dto.EmployeeDto;
import com.avinty.hr.model.Employee;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  EmployeeDto toDto(Employee employee);

  List<EmployeeDto> toDto(List<Employee> employee);

}
