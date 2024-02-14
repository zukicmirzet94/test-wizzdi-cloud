package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Resource;
import com.mirzet.zukic.runtime.request.LoginRequest;
import com.mirzet.zukic.runtime.request.ResourceCreate;
import com.mirzet.zukic.runtime.request.ResourceFilter;
import com.mirzet.zukic.runtime.request.ResourceUpdate;
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
public class ResourceControllerTest {

  private Resource testResource;
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
  public void testResourceCreate() {
    ResourceCreate request = new ResourceCreate();

    ResponseEntity<Resource> response =
        this.restTemplate.postForEntity("/Resource/createResource", request, Resource.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testResource = response.getBody();
    assertResource(request, testResource);
  }

  @Test
  @Order(2)
  public void testListAllResources() {
    ResourceFilter request = new ResourceFilter();
    ParameterizedTypeReference<PaginationResponse<Resource>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Resource>> response =
        this.restTemplate.exchange(
            "/Resource/getAllResources", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Resource> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Resource> Resources = body.getList();
    Assertions.assertNotEquals(0, Resources.size());
    Assertions.assertTrue(Resources.stream().anyMatch(f -> f.getId().equals(testResource.getId())));
  }

  public void assertResource(ResourceCreate request, Resource testResource) {
    Assertions.assertNotNull(testResource);
  }

  @Test
  @Order(3)
  public void testResourceUpdate() {
    ResourceUpdate request = new ResourceUpdate().setId(testResource.getId());
    ResponseEntity<Resource> response =
        this.restTemplate.exchange(
            "/Resource/updateResource", HttpMethod.PUT, new HttpEntity<>(request), Resource.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testResource = response.getBody();
    assertResource(request, testResource);
  }
}
