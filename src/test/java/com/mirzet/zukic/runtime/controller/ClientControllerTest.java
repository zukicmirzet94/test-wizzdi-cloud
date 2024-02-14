package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Client;
import com.mirzet.zukic.runtime.request.ClientCreate;
import com.mirzet.zukic.runtime.request.ClientFilter;
import com.mirzet.zukic.runtime.request.ClientUpdate;
import com.mirzet.zukic.runtime.request.LoginRequest;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppInit.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class ClientControllerTest {

  private Client testClient;
  @Autowired private TestRestTemplate restTemplate;

  @BeforeAll
  private void init() {
    ResponseEntity<Object> authenticationResponse =
        this.restTemplate.postForEntity(
            "/login",
            new LoginRequest().setUsername("admin@flexicore.com").setPassword("admin"),
            Object.class);
    String authenticationKey =
        authenticationResponse.getHeaders().get(HttpHeaders.AUTHORIZATION).stream()
            .findFirst()
            .orElse(null);
    restTemplate
        .getRestTemplate()
        .setInterceptors(
            Collections.singletonList(
                (request, body, execution) -> {
                  request.getHeaders().add("Authorization", "Bearer " + authenticationKey);
                  return execution.execute(request, body);
                }));
  }

  @Test
  @Order(1)
  public void testClientCreate() {
    ClientCreate request = new ClientCreate();

    ResponseEntity<Client> response =
        this.restTemplate.postForEntity("/Client/createClient", request, Client.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testClient = response.getBody();
    assertClient(request, testClient);
  }

  @Test
  @Order(2)
  public void testListAllClients() {
    ClientFilter request = new ClientFilter();
    ParameterizedTypeReference<PaginationResponse<Client>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Client>> response =
        this.restTemplate.exchange(
            "/Client/getAllClients", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Client> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Client> Clients = body.getList();
    Assertions.assertNotEquals(0, Clients.size());
    Assertions.assertTrue(Clients.stream().anyMatch(f -> f.getId().equals(testClient.getId())));
  }

  public void assertClient(ClientCreate request, Client testClient) {
    Assertions.assertNotNull(testClient);
  }

  @Test
  @Order(3)
  public void testClientUpdate() {
    ClientUpdate request = new ClientUpdate().setId(testClient.getId());
    ResponseEntity<Client> response =
        this.restTemplate.exchange(
            "/Client/updateClient", HttpMethod.PUT, new HttpEntity<>(request), Client.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testClient = response.getBody();
    assertClient(request, testClient);
  }
}
