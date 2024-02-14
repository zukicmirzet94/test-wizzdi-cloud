package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.model.EmployeeToActivity;
import com.mirzet.zukic.runtime.model.Role;
import com.mirzet.zukic.runtime.request.EmployeeToActivityCreate;
import com.mirzet.zukic.runtime.request.EmployeeToActivityFilter;
import com.mirzet.zukic.runtime.request.EmployeeToActivityUpdate;
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
public class EmployeeToActivityControllerTest {

  private EmployeeToActivity testEmployeeToActivity;
  @Autowired private TestRestTemplate restTemplate;

  @Autowired private Role role;

  @Autowired private Activity activity;

  @Autowired private Employee employee;

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
  public void testEmployeeToActivityCreate() {
    EmployeeToActivityCreate request = new EmployeeToActivityCreate();

    request.setRoleId(this.role.getId());

    request.setActivityId(this.activity.getId());

    request.setEmployeeId(this.employee.getId());

    ResponseEntity<EmployeeToActivity> response =
        this.restTemplate.postForEntity(
            "/EmployeeToActivity/createEmployeeToActivity", request, EmployeeToActivity.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testEmployeeToActivity = response.getBody();
    assertEmployeeToActivity(request, testEmployeeToActivity);
  }

  @Test
  @Order(2)
  public void testListAllEmployeeToActivities() {
    EmployeeToActivityFilter request = new EmployeeToActivityFilter();
    ParameterizedTypeReference<PaginationResponse<EmployeeToActivity>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<EmployeeToActivity>> response =
        this.restTemplate.exchange(
            "/EmployeeToActivity/getAllEmployeeToActivities",
            HttpMethod.POST,
            new HttpEntity<>(request),
            t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<EmployeeToActivity> body = response.getBody();
    Assertions.assertNotNull(body);
    List<EmployeeToActivity> EmployeeToActivities = body.getList();
    Assertions.assertNotEquals(0, EmployeeToActivities.size());
    Assertions.assertTrue(
        EmployeeToActivities.stream()
            .anyMatch(f -> f.getId().equals(testEmployeeToActivity.getId())));
  }

  public void assertEmployeeToActivity(
      EmployeeToActivityCreate request, EmployeeToActivity testEmployeeToActivity) {
    Assertions.assertNotNull(testEmployeeToActivity);

    if (request.getRoleId() != null) {

      Assertions.assertNotNull(testEmployeeToActivity.getRole());
      Assertions.assertEquals(request.getRoleId(), testEmployeeToActivity.getRole().getId());
    }

    if (request.getActivityId() != null) {

      Assertions.assertNotNull(testEmployeeToActivity.getActivity());
      Assertions.assertEquals(
          request.getActivityId(), testEmployeeToActivity.getActivity().getId());
    }

    if (request.getEmployeeId() != null) {

      Assertions.assertNotNull(testEmployeeToActivity.getEmployee());
      Assertions.assertEquals(
          request.getEmployeeId(), testEmployeeToActivity.getEmployee().getId());
    }
  }

  @Test
  @Order(3)
  public void testEmployeeToActivityUpdate() {
    EmployeeToActivityUpdate request =
        new EmployeeToActivityUpdate().setId(testEmployeeToActivity.getId());
    ResponseEntity<EmployeeToActivity> response =
        this.restTemplate.exchange(
            "/EmployeeToActivity/updateEmployeeToActivity",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            EmployeeToActivity.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testEmployeeToActivity = response.getBody();
    assertEmployeeToActivity(request, testEmployeeToActivity);
  }
}
