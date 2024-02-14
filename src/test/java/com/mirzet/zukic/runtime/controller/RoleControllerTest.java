package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Role;
import com.mirzet.zukic.runtime.request.LoginRequest;
import com.mirzet.zukic.runtime.request.RoleCreate;
import com.mirzet.zukic.runtime.request.RoleFilter;
import com.mirzet.zukic.runtime.request.RoleUpdate;
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
public class RoleControllerTest {

  private Role testRole;
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
  public void testRoleCreate() {
    RoleCreate request = new RoleCreate();

    ResponseEntity<Role> response =
        this.restTemplate.postForEntity("/Role/createRole", request, Role.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testRole = response.getBody();
    assertRole(request, testRole);
  }

  @Test
  @Order(2)
  public void testListAllRoles() {
    RoleFilter request = new RoleFilter();
    ParameterizedTypeReference<PaginationResponse<Role>> t = new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Role>> response =
        this.restTemplate.exchange(
            "/Role/getAllRoles", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Role> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Role> Roles = body.getList();
    Assertions.assertNotEquals(0, Roles.size());
    Assertions.assertTrue(Roles.stream().anyMatch(f -> f.getId().equals(testRole.getId())));
  }

  public void assertRole(RoleCreate request, Role testRole) {
    Assertions.assertNotNull(testRole);
  }

  @Test
  @Order(3)
  public void testRoleUpdate() {
    RoleUpdate request = new RoleUpdate().setId(testRole.getId());
    ResponseEntity<Role> response =
        this.restTemplate.exchange(
            "/Role/updateRole", HttpMethod.PUT, new HttpEntity<>(request), Role.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testRole = response.getBody();
    assertRole(request, testRole);
  }
}
