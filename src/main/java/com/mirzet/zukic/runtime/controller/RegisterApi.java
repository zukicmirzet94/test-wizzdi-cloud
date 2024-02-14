package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.AppUser;
import com.mirzet.zukic.runtime.request.AppUserCreate;
import com.mirzet.zukic.runtime.request.AppUserFilter;
import com.mirzet.zukic.runtime.security.Roles;
import com.mirzet.zukic.runtime.service.AppUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "RegisterApi")
@RestController
public class RegisterApi {

  private final AppUserService appUserService;

  public RegisterApi(AppUserService appUserService) {
    this.appUserService = appUserService;
  }

  @SecurityRequirements
  @PostMapping("register")
  public AppUser register(
      @RequestBody
          @org.springframework.validation.annotation.Validated(
              com.mirzet.zukic.runtime.validation.Create.class)
          AppUserCreate appUserCreate) {
    appUserService
        .listAllAppUsers(
            new AppUserFilter().setUsername(Collections.singleton(appUserCreate.getUsername())),
            null)
        .stream()
        .findAny()
        .ifPresent(
            f -> {
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already exists");
            });
    AppUser appUser = appUserService.createAppUser(appUserCreate.setRoles(Roles.User.name()), null);

    return appUser;
  }
}
