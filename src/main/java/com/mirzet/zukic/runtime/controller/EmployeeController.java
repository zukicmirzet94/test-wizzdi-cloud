package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.request.EmployeeCreate;
import com.mirzet.zukic.runtime.request.EmployeeFilter;
import com.mirzet.zukic.runtime.request.EmployeeUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.EmployeeService;
import com.mirzet.zukic.runtime.validation.Create;
import com.mirzet.zukic.runtime.validation.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Employee")
@Tag(name = "Employee")
public class EmployeeController {

  @Autowired private EmployeeService employeeService;

  @PostMapping("createEmployee")
  @Operation(summary = "createEmployee", description = "Creates Employee")
  public Employee createEmployee(
      @Validated(Create.class) @RequestBody EmployeeCreate employeeCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return employeeService.createEmployee(employeeCreate, securityContext);
  }

  @PostMapping("getAllEmployees")
  @Operation(summary = "getAllEmployees", description = "lists Employees")
  public PaginationResponse<Employee> getAllEmployees(
      @Valid @RequestBody EmployeeFilter employeeFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return employeeService.getAllEmployees(employeeFilter, securityContext);
  }

  @PutMapping("updateEmployee")
  @Operation(summary = "updateEmployee", description = "Updates Employee")
  public Employee updateEmployee(
      @Validated(Update.class) @RequestBody EmployeeUpdate employeeUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return employeeService.updateEmployee(employeeUpdate, securityContext);
  }
}
