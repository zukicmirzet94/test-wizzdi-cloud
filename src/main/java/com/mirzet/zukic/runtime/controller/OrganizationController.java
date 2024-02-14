package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.request.OrganizationCreate;
import com.mirzet.zukic.runtime.request.OrganizationFilter;
import com.mirzet.zukic.runtime.request.OrganizationUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.OrganizationService;
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
@RequestMapping("Organization")
@Tag(name = "Organization")
public class OrganizationController {

  @Autowired private OrganizationService organizationService;

  @PostMapping("createOrganization")
  @Operation(summary = "createOrganization", description = "Creates Organization")
  public Organization createOrganization(
      @Validated(Create.class) @RequestBody OrganizationCreate organizationCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return organizationService.createOrganization(organizationCreate, securityContext);
  }

  @PostMapping("getAllOrganizations")
  @Operation(summary = "getAllOrganizations", description = "lists Organizations")
  public PaginationResponse<Organization> getAllOrganizations(
      @Valid @RequestBody OrganizationFilter organizationFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return organizationService.getAllOrganizations(organizationFilter, securityContext);
  }

  @PutMapping("updateOrganization")
  @Operation(summary = "updateOrganization", description = "Updates Organization")
  public Organization updateOrganization(
      @Validated(Update.class) @RequestBody OrganizationUpdate organizationUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return organizationService.updateOrganization(organizationUpdate, securityContext);
  }
}
