package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Qualification;
import com.mirzet.zukic.runtime.request.QualificationCreate;
import com.mirzet.zukic.runtime.request.QualificationFilter;
import com.mirzet.zukic.runtime.request.QualificationUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.QualificationService;
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
@RequestMapping("Qualification")
@Tag(name = "Qualification")
public class QualificationController {

  @Autowired private QualificationService qualificationService;

  @PostMapping("createQualification")
  @Operation(summary = "createQualification", description = "Creates Qualification")
  public Qualification createQualification(
      @Validated(Create.class) @RequestBody QualificationCreate qualificationCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return qualificationService.createQualification(qualificationCreate, securityContext);
  }

  @PostMapping("getAllQualifications")
  @Operation(summary = "getAllQualifications", description = "lists Qualifications")
  public PaginationResponse<Qualification> getAllQualifications(
      @Valid @RequestBody QualificationFilter qualificationFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return qualificationService.getAllQualifications(qualificationFilter, securityContext);
  }

  @PutMapping("updateQualification")
  @Operation(summary = "updateQualification", description = "Updates Qualification")
  public Qualification updateQualification(
      @Validated(Update.class) @RequestBody QualificationUpdate qualificationUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return qualificationService.updateQualification(qualificationUpdate, securityContext);
  }
}
