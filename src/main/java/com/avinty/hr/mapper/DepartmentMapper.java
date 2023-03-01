package com.avinty.hr.mapper;

import com.avinty.hr.dto.DepartmentDto;
import com.avinty.hr.model.Department;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface DepartmentMapper {

  DepartmentDto toDto(Department department);

  List<DepartmentDto> toDto(List<Department> department);

}
