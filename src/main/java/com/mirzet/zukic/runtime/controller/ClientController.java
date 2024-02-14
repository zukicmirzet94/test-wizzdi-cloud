package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.model.Client;
import com.mirzet.zukic.runtime.request.ClientCreate;
import com.mirzet.zukic.runtime.request.ClientFilter;
import com.mirzet.zukic.runtime.request.ClientUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.ClientService;
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
@RequestMapping("Client")
@Tag(name = "Client")
public class ClientController {

  @Autowired private ClientService clientService;

  @PostMapping("createClient")
  @Operation(summary = "createClient", description = "Creates Client")
  public Client createClient(
      @Validated(Create.class) @RequestBody ClientCreate clientCreate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return clientService.createClient(clientCreate, securityContext);
  }

  @PostMapping("getAllClients")
  @Operation(summary = "getAllClients", description = "lists Clients")
  public PaginationResponse<Client> getAllClients(
      @Valid @RequestBody ClientFilter clientFilter, Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return clientService.getAllClients(clientFilter, securityContext);
  }

  @PutMapping("updateClient")
  @Operation(summary = "updateClient", description = "Updates Client")
  public Client updateClient(
      @Validated(Update.class) @RequestBody ClientUpdate clientUpdate,
      Authentication authentication) {

    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return clientService.updateClient(clientUpdate, securityContext);
  }
}
