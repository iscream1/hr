package com.avinty.hr.dto;

import com.avinty.hr.enums.PositionEnum;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {

  public Long id;
  public String fullName;
  public String email;
  @Enumerated(EnumType.STRING)
  public PositionEnum position;
  public Long departmentId;

}
