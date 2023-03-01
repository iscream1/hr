package com.avinty.hr.validation;

import com.avinty.hr.enums.PositionEnum;
import com.avinty.hr.repository.EmployeeRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import org.springframework.beans.factory.annotation.Autowired;

@SupportedValidationTarget({ValidationTarget.PARAMETERS})
public class ManagerValidator implements ConstraintValidator<ManagerConstraint, Object[]> {

  @Autowired
  EmployeeRepository employeeRepository;

  @Override
  public boolean isValid(Object[] value, ConstraintValidatorContext cxt) {
    return employeeRepository.findById((Long)value[1]).map(employee ->
        PositionEnum.MANAGER.equals(employee.getPosition())).orElse(false);
  }
}
