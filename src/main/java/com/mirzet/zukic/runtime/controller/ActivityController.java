package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.request.ActivityCreate;
import com.mirzet.zukic.runtime.request.ActivityFilter;
import com.mirzet.zukic.runtime.request.ActivityUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.ActivityService;
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
@RequestMapping("Activity")
@Tag(name = "Activity")
public class ActivityController {

  @Autowired private ActivityService activityService;

  @PostMapping("createActivity")
  @Operation(summary = "createActivity", description = "Creates Activity")
  public Activity createActivity(
      @Validated(Create.class) @RequestBody ActivityCreate activityCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return activityService.createActivity(activityCreate, securityContext);
  }

  @PostMapping("getAllActivities")
  @Operation(summary = "getAllActivities", description = "lists Activities")
  public PaginationResponse<Activity> getAllActivities(
      @Valid @RequestBody ActivityFilter activityFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return activityService.getAllActivities(activityFilter, securityContext);
  }

  @PutMapping("updateActivity")
  @Operation(summary = "updateActivity", description = "Updates Activity")
  public Activity updateActivity(
      @Validated(Update.class) @RequestBody ActivityUpdate activityUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return activityService.updateActivity(activityUpdate, securityContext);
  }
}
