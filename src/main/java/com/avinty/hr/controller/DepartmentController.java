package com.avinty.hr.controller;

import com.avinty.hr.dto.DepartmentDto;
import com.avinty.hr.service.DepartmentService;
import com.avinty.hr.validation.ManagerConstraint;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
@CrossOrigin(origins = "${application.cors-origin}")
@RequiredArgsConstructor
@Validated
public class DepartmentController {

  private final DepartmentService departmentService;

  //filter departments by name (contains)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<DepartmentDto> findByName(@RequestParam("name") final String name) {
    return departmentService.findByNameLike(name);
  }

  //set manager of a specific department
  //an employee is only allowed to be set as a manager if its position is MANAGER
  @PatchMapping(path = "{departmentId}/set-manager/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Valid
  @ManagerConstraint
  public ResponseEntity<Void> setManager(@PathVariable("departmentId") final Long departmentId,
      @PathVariable("employeeId") final Long employeeId) {
    departmentService.setManager(departmentId, employeeId);
    return ResponseEntity.ok().build();
  }

  //delete department by ID
  @DeleteMapping(path = "{departmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> deleteById(@PathVariable("departmentId") final Long departmentId) {
    departmentService.delete(departmentId);
    return ResponseEntity.ok().build();
  }

}
