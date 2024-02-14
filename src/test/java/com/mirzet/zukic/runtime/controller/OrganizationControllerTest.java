package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.model.OrganizationType;
import com.mirzet.zukic.runtime.request.LoginRequest;
import com.mirzet.zukic.runtime.request.OrganizationCreate;
import com.mirzet.zukic.runtime.request.OrganizationFilter;
import com.mirzet.zukic.runtime.request.OrganizationUpdate;
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
public class OrganizationControllerTest {

  private Organization testOrganization;
  @Autowired private TestRestTemplate restTemplate;

  @Autowired private OrganizationType organizationType;

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
  public void testOrganizationCreate() {
    OrganizationCreate request = new OrganizationCreate();

    request.setCountry("test-string");

    request.setOrganizationTypeId(this.organizationType.getId());

    request.setType("test-string");

    ResponseEntity<Organization> response =
        this.restTemplate.postForEntity(
            "/Organization/createOrganization", request, Organization.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testOrganization = response.getBody();
    assertOrganization(request, testOrganization);
  }

  @Test
  @Order(2)
  public void testListAllOrganizations() {
    OrganizationFilter request = new OrganizationFilter();
    ParameterizedTypeReference<PaginationResponse<Organization>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Organization>> response =
        this.restTemplate.exchange(
            "/Organization/getAllOrganizations", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Organization> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Organization> Organizations = body.getList();
    Assertions.assertNotEquals(0, Organizations.size());
    Assertions.assertTrue(
        Organizations.stream().anyMatch(f -> f.getId().equals(testOrganization.getId())));
  }

  public void assertOrganization(OrganizationCreate request, Organization testOrganization) {
    Assertions.assertNotNull(testOrganization);

    if (request.getCountry() != null) {
      Assertions.assertEquals(request.getCountry(), testOrganization.getCountry());
    }

    if (request.getOrganizationTypeId() != null) {

      Assertions.assertNotNull(testOrganization.getOrganizationType());
      Assertions.assertEquals(
          request.getOrganizationTypeId(), testOrganization.getOrganizationType().getId());
    }

    if (request.getType() != null) {
      Assertions.assertEquals(request.getType(), testOrganization.getType());
    }
  }

  @Test
  @Order(3)
  public void testOrganizationUpdate() {
    OrganizationUpdate request = new OrganizationUpdate().setId(testOrganization.getId());
    ResponseEntity<Organization> response =
        this.restTemplate.exchange(
            "/Organization/updateOrganization",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            Organization.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testOrganization = response.getBody();
    assertOrganization(request, testOrganization);
  }
}
