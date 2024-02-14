package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Client;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.request.LoginRequest;
import com.mirzet.zukic.runtime.request.ProjectCreate;
import com.mirzet.zukic.runtime.request.ProjectFilter;
import com.mirzet.zukic.runtime.request.ProjectUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import java.time.OffsetDateTime;
import java.time.ZoneId;
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
public class ProjectControllerTest {

  private Project testProject;
  @Autowired private TestRestTemplate restTemplate;

  @Autowired private Client client;

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
  public void testProjectCreate() {
    ProjectCreate request = new ProjectCreate();

    request.setActualEndDate(OffsetDateTime.now());

    request.setPlannedStartDate(OffsetDateTime.now());

    request.setClientId(this.client.getId());

    request.setActualStartDate(OffsetDateTime.now());

    request.setPlannedEndDate(OffsetDateTime.now());

    ResponseEntity<Project> response =
        this.restTemplate.postForEntity("/Project/createProject", request, Project.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testProject = response.getBody();
    assertProject(request, testProject);
  }

  @Test
  @Order(2)
  public void testListAllProjects() {
    ProjectFilter request = new ProjectFilter();
    ParameterizedTypeReference<PaginationResponse<Project>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Project>> response =
        this.restTemplate.exchange(
            "/Project/getAllProjects", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Project> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Project> Projects = body.getList();
    Assertions.assertNotEquals(0, Projects.size());
    Assertions.assertTrue(Projects.stream().anyMatch(f -> f.getId().equals(testProject.getId())));
  }

  public void assertProject(ProjectCreate request, Project testProject) {
    Assertions.assertNotNull(testProject);

    if (request.getActualEndDate() != null) {
      Assertions.assertEquals(
          request.getActualEndDate().atZoneSameInstant(ZoneId.systemDefault()),
          testProject.getActualEndDate().atZoneSameInstant(ZoneId.systemDefault()));
    }

    if (request.getPlannedStartDate() != null) {
      Assertions.assertEquals(
          request.getPlannedStartDate().atZoneSameInstant(ZoneId.systemDefault()),
          testProject.getPlannedStartDate().atZoneSameInstant(ZoneId.systemDefault()));
    }

    if (request.getClientId() != null) {

      Assertions.assertNotNull(testProject.getClient());
      Assertions.assertEquals(request.getClientId(), testProject.getClient().getId());
    }

    if (request.getActualStartDate() != null) {
      Assertions.assertEquals(
          request.getActualStartDate().atZoneSameInstant(ZoneId.systemDefault()),
          testProject.getActualStartDate().atZoneSameInstant(ZoneId.systemDefault()));
    }

    if (request.getPlannedEndDate() != null) {
      Assertions.assertEquals(
          request.getPlannedEndDate().atZoneSameInstant(ZoneId.systemDefault()),
          testProject.getPlannedEndDate().atZoneSameInstant(ZoneId.systemDefault()));
    }
  }

  @Test
  @Order(3)
  public void testProjectUpdate() {
    ProjectUpdate request = new ProjectUpdate().setId(testProject.getId());
    ResponseEntity<Project> response =
        this.restTemplate.exchange(
            "/Project/updateProject", HttpMethod.PUT, new HttpEntity<>(request), Project.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testProject = response.getBody();
    assertProject(request, testProject);
  }
}
