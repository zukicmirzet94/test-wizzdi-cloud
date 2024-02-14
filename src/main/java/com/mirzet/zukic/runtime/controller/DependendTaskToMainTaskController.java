package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.DependendTaskToMainTask;
import com.mirzet.zukic.runtime.request.DependendTaskToMainTaskCreate;
import com.mirzet.zukic.runtime.request.DependendTaskToMainTaskFilter;
import com.mirzet.zukic.runtime.request.DependendTaskToMainTaskUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.DependendTaskToMainTaskService;
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
@RequestMapping("DependendTaskToMainTask")
@Tag(name = "DependendTaskToMainTask")
public class DependendTaskToMainTaskController {

  @Autowired private DependendTaskToMainTaskService dependendTaskToMainTaskService;

  @PostMapping("createDependendTaskToMainTask")
  @Operation(
      summary = "createDependendTaskToMainTask",
      description = "Creates DependendTaskToMainTask")
  public DependendTaskToMainTask createDependendTaskToMainTask(
      @Validated(Create.class) @RequestBody
          DependendTaskToMainTaskCreate dependendTaskToMainTaskCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return dependendTaskToMainTaskService.createDependendTaskToMainTask(
        dependendTaskToMainTaskCreate, securityContext);
  }

  @PostMapping("getAllDependendTaskToMainTasks")
  @Operation(
      summary = "getAllDependendTaskToMainTasks",
      description = "lists DependendTaskToMainTasks")
  public PaginationResponse<DependendTaskToMainTask> getAllDependendTaskToMainTasks(
      @Valid @RequestBody DependendTaskToMainTaskFilter dependendTaskToMainTaskFilter,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return dependendTaskToMainTaskService.getAllDependendTaskToMainTasks(
        dependendTaskToMainTaskFilter, securityContext);
  }

  @PutMapping("updateDependendTaskToMainTask")
  @Operation(
      summary = "updateDependendTaskToMainTask",
      description = "Updates DependendTaskToMainTask")
  public DependendTaskToMainTask updateDependendTaskToMainTask(
      @Validated(Update.class) @RequestBody
          DependendTaskToMainTaskUpdate dependendTaskToMainTaskUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return dependendTaskToMainTaskService.updateDependendTaskToMainTask(
        dependendTaskToMainTaskUpdate, securityContext);
  }
}
