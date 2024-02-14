package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.request.ProjectCreate;
import com.mirzet.zukic.runtime.request.ProjectFilter;
import com.mirzet.zukic.runtime.request.ProjectUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.ProjectService;
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
@RequestMapping("Project")
@Tag(name = "Project")
public class ProjectController {

  @Autowired private ProjectService projectService;

  @PostMapping("createProject")
  @Operation(summary = "createProject", description = "Creates Project")
  public Project createProject(
      @Validated(Create.class) @RequestBody ProjectCreate projectCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return projectService.createProject(projectCreate, securityContext);
  }

  @PostMapping("getAllProjects")
  @Operation(summary = "getAllProjects", description = "lists Projects")
  public PaginationResponse<Project> getAllProjects(
      @Valid @RequestBody ProjectFilter projectFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return projectService.getAllProjects(projectFilter, securityContext);
  }

  @PutMapping("updateProject")
  @Operation(summary = "updateProject", description = "Updates Project")
  public Project updateProject(
      @Validated(Update.class) @RequestBody ProjectUpdate projectUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return projectService.updateProject(projectUpdate, securityContext);
  }
}
