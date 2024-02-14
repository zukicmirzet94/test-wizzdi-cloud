package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.PersonToProject;
import com.mirzet.zukic.runtime.request.PersonToProjectCreate;
import com.mirzet.zukic.runtime.request.PersonToProjectFilter;
import com.mirzet.zukic.runtime.request.PersonToProjectUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.PersonToProjectService;
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
@RequestMapping("PersonToProject")
@Tag(name = "PersonToProject")
public class PersonToProjectController {

  @Autowired private PersonToProjectService personToProjectService;

  @PostMapping("createPersonToProject")
  @Operation(summary = "createPersonToProject", description = "Creates PersonToProject")
  public PersonToProject createPersonToProject(
      @Validated(Create.class) @RequestBody PersonToProjectCreate personToProjectCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return personToProjectService.createPersonToProject(personToProjectCreate, securityContext);
  }

  @PostMapping("getAllPersonToProjects")
  @Operation(summary = "getAllPersonToProjects", description = "lists PersonToProjects")
  public PaginationResponse<PersonToProject> getAllPersonToProjects(
      @Valid @RequestBody PersonToProjectFilter personToProjectFilter,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return personToProjectService.getAllPersonToProjects(personToProjectFilter, securityContext);
  }

  @PutMapping("updatePersonToProject")
  @Operation(summary = "updatePersonToProject", description = "Updates PersonToProject")
  public PersonToProject updatePersonToProject(
      @Validated(Update.class) @RequestBody PersonToProjectUpdate personToProjectUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return personToProjectService.updatePersonToProject(personToProjectUpdate, securityContext);
  }
}
