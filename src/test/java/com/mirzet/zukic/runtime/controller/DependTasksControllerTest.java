package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.DependTasks;
import com.mirzet.zukic.runtime.request.DependTasksCreate;
import com.mirzet.zukic.runtime.request.DependTasksFilter;
import com.mirzet.zukic.runtime.request.DependTasksUpdate;
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
public class DependTasksControllerTest {

  private DependTasks testDependTasks;
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
  public void testDependTasksCreate() {
    DependTasksCreate request = new DependTasksCreate();

    ResponseEntity<DependTasks> response =
        this.restTemplate.postForEntity(
            "/DependTasks/createDependTasks", request, DependTasks.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testDependTasks = response.getBody();
    assertDependTasks(request, testDependTasks);
  }

  @Test
  @Order(2)
  public void testListAllDependTaskses() {
    DependTasksFilter request = new DependTasksFilter();
    ParameterizedTypeReference<PaginationResponse<DependTasks>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<DependTasks>> response =
        this.restTemplate.exchange(
            "/DependTasks/getAllDependTaskses", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<DependTasks> body = response.getBody();
    Assertions.assertNotNull(body);
    List<DependTasks> DependTaskses = body.getList();
    Assertions.assertNotEquals(0, DependTaskses.size());
    Assertions.assertTrue(
        DependTaskses.stream().anyMatch(f -> f.getId().equals(testDependTasks.getId())));
  }

  public void assertDependTasks(DependTasksCreate request, DependTasks testDependTasks) {
    Assertions.assertNotNull(testDependTasks);
  }

  @Test
  @Order(3)
  public void testDependTasksUpdate() {
    DependTasksUpdate request = new DependTasksUpdate().setId(testDependTasks.getId());
    ResponseEntity<DependTasks> response =
        this.restTemplate.exchange(
            "/DependTasks/updateDependTasks",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            DependTasks.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testDependTasks = response.getBody();
    assertDependTasks(request, testDependTasks);
  }
}
