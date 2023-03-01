package com.avinty.hr.controller;

import com.avinty.hr.dto.EmployeeDto;
import com.avinty.hr.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "${application.cors-origin}")
@RequiredArgsConstructor
public class EmployeesController {

  private final EmployeeService employeeService;

  //find an employee with name and/or email filtering (contains)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<EmployeeDto> find(@RequestParam(required = false, value = "name") final String name,
      @RequestParam(required = false, value = "email") String email) {
    return employeeService.findByNameAndEmailLike(name, email);
  }

}
