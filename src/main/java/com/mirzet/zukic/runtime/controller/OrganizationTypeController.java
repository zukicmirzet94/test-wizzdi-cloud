package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.OrganizationType;
import com.mirzet.zukic.runtime.request.OrganizationTypeCreate;
import com.mirzet.zukic.runtime.request.OrganizationTypeFilter;
import com.mirzet.zukic.runtime.request.OrganizationTypeUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.OrganizationTypeService;
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
@RequestMapping("OrganizationType")
@Tag(name = "OrganizationType")
public class OrganizationTypeController {

  @Autowired private OrganizationTypeService organizationTypeService;

  @PostMapping("createOrganizationType")
  @Operation(summary = "createOrganizationType", description = "Creates OrganizationType")
  public OrganizationType createOrganizationType(
      @Validated(Create.class) @RequestBody OrganizationTypeCreate organizationTypeCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return organizationTypeService.createOrganizationType(organizationTypeCreate, securityContext);
  }

  @PostMapping("getAllOrganizationTypes")
  @Operation(summary = "getAllOrganizationTypes", description = "lists OrganizationTypes")
  public PaginationResponse<OrganizationType> getAllOrganizationTypes(
      @Valid @RequestBody OrganizationTypeFilter organizationTypeFilter,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return organizationTypeService.getAllOrganizationTypes(organizationTypeFilter, securityContext);
  }

  @PutMapping("updateOrganizationType")
  @Operation(summary = "updateOrganizationType", description = "Updates OrganizationType")
  public OrganizationType updateOrganizationType(
      @Validated(Update.class) @RequestBody OrganizationTypeUpdate organizationTypeUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return organizationTypeService.updateOrganizationType(organizationTypeUpdate, securityContext);
  }
}
