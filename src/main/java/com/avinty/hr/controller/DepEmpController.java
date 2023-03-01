package com.avinty.hr.controller;

import com.avinty.hr.dto.DepartmentDto;
import com.avinty.hr.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dep-emp")
@CrossOrigin(origins = "${application.cors-origin}")
@RequiredArgsConstructor
public class DepEmpController {

  private final DepartmentService departmentService;

  //list all departments with their employees
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<DepartmentDto> findAll() {
    return departmentService.findAll();
  }

}
