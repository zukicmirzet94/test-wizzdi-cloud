package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.model.Priority;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.request.ActivityCreate;
import com.mirzet.zukic.runtime.request.ActivityFilter;
import com.mirzet.zukic.runtime.request.ActivityUpdate;
import com.mirzet.zukic.runtime.request.LoginRequest;
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
public class ActivityControllerTest {

  private Activity testActivity;
  @Autowired private TestRestTemplate restTemplate;

  @Autowired private Task task;

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
  public void testActivityCreate() {
    ActivityCreate request = new ActivityCreate();

    request.setActualEndDate(OffsetDateTime.now());

    request.setPriority(Priority.VeryLow);

    request.setPlannedEndDate(OffsetDateTime.now());

    request.setActualStartDate(OffsetDateTime.now());

    request.setTaskId(this.task.getId());

    request.setPlannedBudget(10D);

    request.setPlannedStartDate(OffsetDateTime.now());

    request.setActualBudget(10D);

    ResponseEntity<Activity> response =
        this.restTemplate.postForEntity("/Activity/createActivity", request, Activity.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testActivity = response.getBody();
    assertActivity(request, testActivity);
  }

  @Test
  @Order(2)
  public void testListAllActivities() {
    ActivityFilter request = new ActivityFilter();
    ParameterizedTypeReference<PaginationResponse<Activity>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Activity>> response =
        this.restTemplate.exchange(
            "/Activity/getAllActivities", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Activity> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Activity> Activities = body.getList();
    Assertions.assertNotEquals(0, Activities.size());
    Assertions.assertTrue(
        Activities.stream().anyMatch(f -> f.getId().equals(testActivity.getId())));
  }

  public void assertActivity(ActivityCreate request, Activity testActivity) {
    Assertions.assertNotNull(testActivity);

    if (request.getActualEndDate() != null) {
      Assertions.assertEquals(
          request.getActualEndDate().atZoneSameInstant(ZoneId.systemDefault()),
          testActivity.getActualEndDate().atZoneSameInstant(ZoneId.systemDefault()));
    }

    if (request.getPriority() != null) {
      Assertions.assertEquals(request.getPriority(), testActivity.getPriority());
    }

    if (request.getPlannedEndDate() != null) {
      Assertions.assertEquals(
          request.getPlannedEndDate().atZoneSameInstant(ZoneId.systemDefault()),
          testActivity.getPlannedEndDate().atZoneSameInstant(ZoneId.systemDefault()));
    }

    if (request.getActualStartDate() != null) {
      Assertions.assertEquals(
          request.getActualStartDate().atZoneSameInstant(ZoneId.systemDefault()),
          testActivity.getActualStartDate().atZoneSameInstant(ZoneId.systemDefault()));
    }

    if (request.getTaskId() != null) {

      Assertions.assertNotNull(testActivity.getTask());
      Assertions.assertEquals(request.getTaskId(), testActivity.getTask().getId());
    }

    if (request.getPlannedBudget() != null) {
      Assertions.assertEquals(request.getPlannedBudget(), testActivity.getPlannedBudget());
    }

    if (request.getPlannedStartDate() != null) {
      Assertions.assertEquals(
          request.getPlannedStartDate().atZoneSameInstant(ZoneId.systemDefault()),
          testActivity.getPlannedStartDate().atZoneSameInstant(ZoneId.systemDefault()));
    }

    if (request.getActualBudget() != null) {
      Assertions.assertEquals(request.getActualBudget(), testActivity.getActualBudget());
    }
  }

  @Test
  @Order(3)
  public void testActivityUpdate() {
    ActivityUpdate request = new ActivityUpdate().setId(testActivity.getId());
    ResponseEntity<Activity> response =
        this.restTemplate.exchange(
            "/Activity/updateActivity", HttpMethod.PUT, new HttpEntity<>(request), Activity.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testActivity = response.getBody();
    assertActivity(request, testActivity);
  }
}
