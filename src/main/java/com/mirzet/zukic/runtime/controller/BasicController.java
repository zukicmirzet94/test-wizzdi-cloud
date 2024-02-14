package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Basic;
import com.mirzet.zukic.runtime.request.BasicCreate;
import com.mirzet.zukic.runtime.request.BasicFilter;
import com.mirzet.zukic.runtime.request.BasicUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.BasicService;
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
@RequestMapping("Basic")
@Tag(name = "Basic")
public class BasicController {

  @Autowired private BasicService basicService;

  @PostMapping("createBasic")
  @Operation(summary = "createBasic", description = "Creates Basic")
  public Basic createBasic(
      @Validated(Create.class) @RequestBody BasicCreate basicCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return basicService.createBasic(basicCreate, securityContext);
  }

  @PostMapping("getAllBasics")
  @Operation(summary = "getAllBasics", description = "lists Basics")
  public PaginationResponse<Basic> getAllBasics(
      @Valid @RequestBody BasicFilter basicFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return basicService.getAllBasics(basicFilter, securityContext);
  }

  @PutMapping("updateBasic")
  @Operation(summary = "updateBasic", description = "Updates Basic")
  public Basic updateBasic(
      @Validated(Update.class) @RequestBody BasicUpdate basicUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return basicService.updateBasic(basicUpdate, securityContext);
  }
}
