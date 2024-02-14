package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.model.Resource;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.model.TaskStatus;
import com.mirzet.zukic.runtime.request.LoginRequest;
import com.mirzet.zukic.runtime.request.TaskCreate;
import com.mirzet.zukic.runtime.request.TaskFilter;
import com.mirzet.zukic.runtime.request.TaskUpdate;
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
public class TaskControllerTest {

  private Task testTask;
  @Autowired private TestRestTemplate restTemplate;

  @Autowired private Resource resource;

  @Autowired private Project project;

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
  public void testTaskCreate() {
    TaskCreate request = new TaskCreate();

    request.setStoryPoints(10);

    request.setResourceId(this.resource.getId());

    request.setProjectId(this.project.getId());

    request.setTaskStatus(TaskStatus.Backlog);

    ResponseEntity<Task> response =
        this.restTemplate.postForEntity("/Task/createTask", request, Task.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testTask = response.getBody();
    assertTask(request, testTask);
  }

  @Test
  @Order(2)
  public void testListAllTasks() {
    TaskFilter request = new TaskFilter();
    ParameterizedTypeReference<PaginationResponse<Task>> t = new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Task>> response =
        this.restTemplate.exchange(
            "/Task/getAllTasks", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Task> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Task> Tasks = body.getList();
    Assertions.assertNotEquals(0, Tasks.size());
    Assertions.assertTrue(Tasks.stream().anyMatch(f -> f.getId().equals(testTask.getId())));
  }

  public void assertTask(TaskCreate request, Task testTask) {
    Assertions.assertNotNull(testTask);

    if (request.getStoryPoints() != null) {
      Assertions.assertEquals(request.getStoryPoints(), testTask.getStoryPoints());
    }

    if (request.getResourceId() != null) {

      Assertions.assertNotNull(testTask.getResource());
      Assertions.assertEquals(request.getResourceId(), testTask.getResource().getId());
    }

    if (request.getProjectId() != null) {

      Assertions.assertNotNull(testTask.getProject());
      Assertions.assertEquals(request.getProjectId(), testTask.getProject().getId());
    }

    if (request.getTaskStatus() != null) {
      Assertions.assertEquals(request.getTaskStatus(), testTask.getTaskStatus());
    }
  }

  @Test
  @Order(3)
  public void testTaskUpdate() {
    TaskUpdate request = new TaskUpdate().setId(testTask.getId());
    ResponseEntity<Task> response =
        this.restTemplate.exchange(
            "/Task/updateTask", HttpMethod.PUT, new HttpEntity<>(request), Task.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testTask = response.getBody();
    assertTask(request, testTask);
  }
}
