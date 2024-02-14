package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Role;
import com.mirzet.zukic.runtime.request.RoleCreate;
import com.mirzet.zukic.runtime.request.RoleFilter;
import com.mirzet.zukic.runtime.request.RoleUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.RoleService;
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
@RequestMapping("Role")
@Tag(name = "Role")
public class RoleController {

  @Autowired private RoleService roleService;

  @PostMapping("createRole")
  @Operation(summary = "createRole", description = "Creates Role")
  public Role createRole(
      @Validated(Create.class) @RequestBody RoleCreate roleCreate, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return roleService.createRole(roleCreate, securityContext);
  }

  @PostMapping("getAllRoles")
  @Operation(summary = "getAllRoles", description = "lists Roles")
  public PaginationResponse<Role> getAllRoles(
      @Valid @RequestBody RoleFilter roleFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return roleService.getAllRoles(roleFilter, securityContext);
  }

  @PutMapping("updateRole")
  @Operation(summary = "updateRole", description = "Updates Role")
  public Role updateRole(
      @Validated(Update.class) @RequestBody RoleUpdate roleUpdate, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return roleService.updateRole(roleUpdate, securityContext);
  }
}
