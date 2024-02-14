package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.request.EmployeeToActivityCreate;
import com.mirzet.zukic.runtime.request.EmployeeToActivityFilter;
import com.mirzet.zukic.runtime.request.EmployeeToActivityUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.EmployeeToActivityService;
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
@RequestMapping("EmployeeToActivity")
@Tag(name = "EmployeeToActivity")
public class EmployeeToActivityController {

  @Autowired private EmployeeToActivityService employeeToActivityService;

  @PostMapping("createEmployeeToActivity")
  @Operation(summary = "createEmployeeToActivity", description = "Creates EmployeeToActivity")
  public EmployeeToActivity createEmployeeToActivity(
      @Validated(Create.class) @RequestBody EmployeeToActivityCreate employeeToActivityCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return employeeToActivityService.createEmployeeToActivity(
        employeeToActivityCreate, securityContext);
  }

  @PostMapping("getAllEmployeeToActivities")
  @Operation(summary = "getAllEmployeeToActivities", description = "lists EmployeeToActivities")
  public PaginationResponse<EmployeeToActivity> getAllEmployeeToActivities(
      @Valid @RequestBody EmployeeToActivityFilter employeeToActivityFilter,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return employeeToActivityService.getAllEmployeeToActivities(
        employeeToActivityFilter, securityContext);
  }

  @PutMapping("updateEmployeeToActivity")
  @Operation(summary = "updateEmployeeToActivity", description = "Updates EmployeeToActivity")
  public EmployeeToActivity updateEmployeeToActivity(
      @Validated(Update.class) @RequestBody EmployeeToActivityUpdate employeeToActivityUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return employeeToActivityService.updateEmployeeToActivity(
        employeeToActivityUpdate, securityContext);
  }
}
