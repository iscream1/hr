package com.avinty.hr.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDto {

  public Long id;
  public String name;
  public Long managerId;

  List<EmployeeDto> employeeList = new ArrayList<>();

}
