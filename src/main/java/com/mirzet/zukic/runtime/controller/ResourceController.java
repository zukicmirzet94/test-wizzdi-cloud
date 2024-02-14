package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Resource;
import com.mirzet.zukic.runtime.request.ResourceCreate;
import com.mirzet.zukic.runtime.request.ResourceFilter;
import com.mirzet.zukic.runtime.request.ResourceUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.ResourceService;
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
@RequestMapping("Resource")
@Tag(name = "Resource")
public class ResourceController {

  @Autowired private ResourceService resourceService;

  @PostMapping("createResource")
  @Operation(summary = "createResource", description = "Creates Resource")
  public Resource createResource(
      @Validated(Create.class) @RequestBody ResourceCreate resourceCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return resourceService.createResource(resourceCreate, securityContext);
  }

  @PostMapping("getAllResources")
  @Operation(summary = "getAllResources", description = "lists Resources")
  public PaginationResponse<Resource> getAllResources(
      @Valid @RequestBody ResourceFilter resourceFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return resourceService.getAllResources(resourceFilter, securityContext);
  }

  @PutMapping("updateResource")
  @Operation(summary = "updateResource", description = "Updates Resource")
  public Resource updateResource(
      @Validated(Update.class) @RequestBody ResourceUpdate resourceUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return resourceService.updateResource(resourceUpdate, securityContext);
  }
}
