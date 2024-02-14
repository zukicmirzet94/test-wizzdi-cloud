package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Person;
import com.mirzet.zukic.runtime.request.PersonCreate;
import com.mirzet.zukic.runtime.request.PersonFilter;
import com.mirzet.zukic.runtime.request.PersonUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.PersonService;
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
@RequestMapping("Person")
@Tag(name = "Person")
public class PersonController {

  @Autowired private PersonService personService;

  @PostMapping("createPerson")
  @Operation(summary = "createPerson", description = "Creates Person")
  public Person createPerson(
      @Validated(Create.class) @RequestBody PersonCreate personCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return personService.createPerson(personCreate, securityContext);
  }

  @PostMapping("getAllPersons")
  @Operation(summary = "getAllPersons", description = "lists Persons")
  public PaginationResponse<Person> getAllPersons(
      @Valid @RequestBody PersonFilter personFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return personService.getAllPersons(personFilter, securityContext);
  }

  @PutMapping("updatePerson")
  @Operation(summary = "updatePerson", description = "Updates Person")
  public Person updatePerson(
      @Validated(Update.class) @RequestBody PersonUpdate personUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return personService.updatePerson(personUpdate, securityContext);
  }
}
