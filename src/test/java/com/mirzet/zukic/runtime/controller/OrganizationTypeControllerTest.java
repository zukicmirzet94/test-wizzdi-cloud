package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.OrganizationType;
import com.mirzet.zukic.runtime.request.LoginRequest;
import com.mirzet.zukic.runtime.request.OrganizationTypeCreate;
import com.mirzet.zukic.runtime.request.OrganizationTypeFilter;
import com.mirzet.zukic.runtime.request.OrganizationTypeUpdate;
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
public class OrganizationTypeControllerTest {

  private OrganizationType testOrganizationType;
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
  public void testOrganizationTypeCreate() {
    OrganizationTypeCreate request = new OrganizationTypeCreate();

    ResponseEntity<OrganizationType> response =
        this.restTemplate.postForEntity(
            "/OrganizationType/createOrganizationType", request, OrganizationType.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testOrganizationType = response.getBody();
    assertOrganizationType(request, testOrganizationType);
  }

  @Test
  @Order(2)
  public void testListAllOrganizationTypes() {
    OrganizationTypeFilter request = new OrganizationTypeFilter();
    ParameterizedTypeReference<PaginationResponse<OrganizationType>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<OrganizationType>> response =
        this.restTemplate.exchange(
            "/OrganizationType/getAllOrganizationTypes",
            HttpMethod.POST,
            new HttpEntity<>(request),
            t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<OrganizationType> body = response.getBody();
    Assertions.assertNotNull(body);
    List<OrganizationType> OrganizationTypes = body.getList();
    Assertions.assertNotEquals(0, OrganizationTypes.size());
    Assertions.assertTrue(
        OrganizationTypes.stream().anyMatch(f -> f.getId().equals(testOrganizationType.getId())));
  }

  public void assertOrganizationType(
      OrganizationTypeCreate request, OrganizationType testOrganizationType) {
    Assertions.assertNotNull(testOrganizationType);
  }

  @Test
  @Order(3)
  public void testOrganizationTypeUpdate() {
    OrganizationTypeUpdate request =
        new OrganizationTypeUpdate().setId(testOrganizationType.getId());
    ResponseEntity<OrganizationType> response =
        this.restTemplate.exchange(
            "/OrganizationType/updateOrganizationType",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            OrganizationType.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testOrganizationType = response.getBody();
    assertOrganizationType(request, testOrganizationType);
  }
}
