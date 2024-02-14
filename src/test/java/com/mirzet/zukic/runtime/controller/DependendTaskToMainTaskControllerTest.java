package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.DependTasks;
import com.mirzet.zukic.runtime.model.DependendTaskToMainTask;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.request.DependendTaskToMainTaskCreate;
import com.mirzet.zukic.runtime.request.DependendTaskToMainTaskFilter;
import com.mirzet.zukic.runtime.request.DependendTaskToMainTaskUpdate;
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
public class DependendTaskToMainTaskControllerTest {

  private DependendTaskToMainTask testDependendTaskToMainTask;
  @Autowired private TestRestTemplate restTemplate;

  @Autowired private Task task;

  @Autowired private DependTasks dependTasks;

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
  public void testDependendTaskToMainTaskCreate() {
    DependendTaskToMainTaskCreate request = new DependendTaskToMainTaskCreate();

    request.setDependTasksId(this.dependTasks.getId());

    request.setTaskId(this.task.getId());

    ResponseEntity<DependendTaskToMainTask> response =
        this.restTemplate.postForEntity(
            "/DependendTaskToMainTask/createDependendTaskToMainTask",
            request,
            DependendTaskToMainTask.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testDependendTaskToMainTask = response.getBody();
    assertDependendTaskToMainTask(request, testDependendTaskToMainTask);
  }

  @Test
  @Order(2)
  public void testListAllDependendTaskToMainTasks() {
    DependendTaskToMainTaskFilter request = new DependendTaskToMainTaskFilter();
    ParameterizedTypeReference<PaginationResponse<DependendTaskToMainTask>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<DependendTaskToMainTask>> response =
        this.restTemplate.exchange(
            "/DependendTaskToMainTask/getAllDependendTaskToMainTasks",
            HttpMethod.POST,
            new HttpEntity<>(request),
            t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<DependendTaskToMainTask> body = response.getBody();
    Assertions.assertNotNull(body);
    List<DependendTaskToMainTask> DependendTaskToMainTasks = body.getList();
    Assertions.assertNotEquals(0, DependendTaskToMainTasks.size());
    Assertions.assertTrue(
        DependendTaskToMainTasks.stream()
            .anyMatch(f -> f.getId().equals(testDependendTaskToMainTask.getId())));
  }

  public void assertDependendTaskToMainTask(
      DependendTaskToMainTaskCreate request, DependendTaskToMainTask testDependendTaskToMainTask) {
    Assertions.assertNotNull(testDependendTaskToMainTask);

    if (request.getDependTasksId() != null) {

      Assertions.assertNotNull(testDependendTaskToMainTask.getDependTasks());
      Assertions.assertEquals(
          request.getDependTasksId(), testDependendTaskToMainTask.getDependTasks().getId());
    }

    if (request.getTaskId() != null) {

      Assertions.assertNotNull(testDependendTaskToMainTask.getTask());
      Assertions.assertEquals(request.getTaskId(), testDependendTaskToMainTask.getTask().getId());
    }
  }

  @Test
  @Order(3)
  public void testDependendTaskToMainTaskUpdate() {
    DependendTaskToMainTaskUpdate request =
        new DependendTaskToMainTaskUpdate().setId(testDependendTaskToMainTask.getId());
    ResponseEntity<DependendTaskToMainTask> response =
        this.restTemplate.exchange(
            "/DependendTaskToMainTask/updateDependendTaskToMainTask",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            DependendTaskToMainTask.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testDependendTaskToMainTask = response.getBody();
    assertDependendTaskToMainTask(request, testDependendTaskToMainTask);
  }
}
