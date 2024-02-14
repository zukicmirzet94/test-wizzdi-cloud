package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.model.Qualification;
import com.mirzet.zukic.runtime.request.EmployeeCreate;
import com.mirzet.zukic.runtime.request.EmployeeFilter;
import com.mirzet.zukic.runtime.request.EmployeeUpdate;
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
public class EmployeeControllerTest {

  private Employee testEmployee;
  @Autowired private TestRestTemplate restTemplate;

  @Autowired private Qualification qualification;

  @Autowired private Organization organization;

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
  public void testEmployeeCreate() {
    EmployeeCreate request = new EmployeeCreate();

    request.setOrganizationId(this.organization.getId());

    request.setQualificationId(this.qualification.getId());

    ResponseEntity<Employee> response =
        this.restTemplate.postForEntity("/Employee/createEmployee", request, Employee.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testEmployee = response.getBody();
    assertEmployee(request, testEmployee);
  }

  @Test
  @Order(2)
  public void testListAllEmployees() {
    EmployeeFilter request = new EmployeeFilter();
    ParameterizedTypeReference<PaginationResponse<Employee>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Employee>> response =
        this.restTemplate.exchange(
            "/Employee/getAllEmployees", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Employee> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Employee> Employees = body.getList();
    Assertions.assertNotEquals(0, Employees.size());
    Assertions.assertTrue(Employees.stream().anyMatch(f -> f.getId().equals(testEmployee.getId())));
  }

  public void assertEmployee(EmployeeCreate request, Employee testEmployee) {
    Assertions.assertNotNull(testEmployee);

    if (request.getOrganizationId() != null) {

      Assertions.assertNotNull(testEmployee.getOrganization());
      Assertions.assertEquals(request.getOrganizationId(), testEmployee.getOrganization().getId());
    }

    if (request.getQualificationId() != null) {

      Assertions.assertNotNull(testEmployee.getQualification());
      Assertions.assertEquals(
          request.getQualificationId(), testEmployee.getQualification().getId());
    }
  }

  @Test
  @Order(3)
  public void testEmployeeUpdate() {
    EmployeeUpdate request = new EmployeeUpdate().setId(testEmployee.getId());
    ResponseEntity<Employee> response =
        this.restTemplate.exchange(
            "/Employee/updateEmployee", HttpMethod.PUT, new HttpEntity<>(request), Employee.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testEmployee = response.getBody();
    assertEmployee(request, testEmployee);
  }
}
