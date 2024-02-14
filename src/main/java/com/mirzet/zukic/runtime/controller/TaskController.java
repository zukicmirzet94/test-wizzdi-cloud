package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.request.TaskCreate;
import com.mirzet.zukic.runtime.request.TaskFilter;
import com.mirzet.zukic.runtime.request.TaskUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.TaskService;
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
@RequestMapping("Task")
@Tag(name = "Task")
public class TaskController {

  @Autowired private TaskService taskService;

  @PostMapping("createTask")
  @Operation(summary = "createTask", description = "Creates Task")
  public Task createTask(
      @Validated(Create.class) @RequestBody TaskCreate taskCreate, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return taskService.createTask(taskCreate, securityContext);
  }

  @PostMapping("getAllTasks")
  @Operation(summary = "getAllTasks", description = "lists Tasks")
  public PaginationResponse<Task> getAllTasks(
      @Valid @RequestBody TaskFilter taskFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return taskService.getAllTasks(taskFilter, securityContext);
  }

  @PutMapping("updateTask")
  @Operation(summary = "updateTask", description = "Updates Task")
  public Task updateTask(
      @Validated(Update.class) @RequestBody TaskUpdate taskUpdate, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return taskService.updateTask(taskUpdate, securityContext);
  }
}
