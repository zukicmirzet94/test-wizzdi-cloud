package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.DependTasks;
import com.mirzet.zukic.runtime.request.DependTasksCreate;
import com.mirzet.zukic.runtime.request.DependTasksFilter;
import com.mirzet.zukic.runtime.request.DependTasksUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.DependTasksService;
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
@RequestMapping("DependTasks")
@Tag(name = "DependTasks")
public class DependTasksController {

  @Autowired private DependTasksService dependTasksService;

  @PostMapping("createDependTasks")
  @Operation(summary = "createDependTasks", description = "Creates DependTasks")
  public DependTasks createDependTasks(
      @Validated(Create.class) @RequestBody DependTasksCreate dependTasksCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return dependTasksService.createDependTasks(dependTasksCreate, securityContext);
  }

  @PostMapping("getAllDependTaskses")
  @Operation(summary = "getAllDependTaskses", description = "lists DependTaskses")
  public PaginationResponse<DependTasks> getAllDependTaskses(
      @Valid @RequestBody DependTasksFilter dependTasksFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return dependTasksService.getAllDependTaskses(dependTasksFilter, securityContext);
  }

  @PutMapping("updateDependTasks")
  @Operation(summary = "updateDependTasks", description = "Updates DependTasks")
  public DependTasks updateDependTasks(
      @Validated(Update.class) @RequestBody DependTasksUpdate dependTasksUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return dependTasksService.updateDependTasks(dependTasksUpdate, securityContext);
  }
}
