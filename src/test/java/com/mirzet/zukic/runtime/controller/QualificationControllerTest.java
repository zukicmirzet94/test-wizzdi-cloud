package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Qualification;
import com.mirzet.zukic.runtime.request.LoginRequest;
import com.mirzet.zukic.runtime.request.QualificationCreate;
import com.mirzet.zukic.runtime.request.QualificationFilter;
import com.mirzet.zukic.runtime.request.QualificationUpdate;
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
public class QualificationControllerTest {

  private Qualification testQualification;
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
  public void testQualificationCreate() {
    QualificationCreate request = new QualificationCreate();

    ResponseEntity<Qualification> response =
        this.restTemplate.postForEntity(
            "/Qualification/createQualification", request, Qualification.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testQualification = response.getBody();
    assertQualification(request, testQualification);
  }

  @Test
  @Order(2)
  public void testListAllQualifications() {
    QualificationFilter request = new QualificationFilter();
    ParameterizedTypeReference<PaginationResponse<Qualification>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Qualification>> response =
        this.restTemplate.exchange(
            "/Qualification/getAllQualifications", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Qualification> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Qualification> Qualifications = body.getList();
    Assertions.assertNotEquals(0, Qualifications.size());
    Assertions.assertTrue(
        Qualifications.stream().anyMatch(f -> f.getId().equals(testQualification.getId())));
  }

  public void assertQualification(QualificationCreate request, Qualification testQualification) {
    Assertions.assertNotNull(testQualification);
  }

  @Test
  @Order(3)
  public void testQualificationUpdate() {
    QualificationUpdate request = new QualificationUpdate().setId(testQualification.getId());
    ResponseEntity<Qualification> response =
        this.restTemplate.exchange(
            "/Qualification/updateQualification",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            Qualification.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testQualification = response.getBody();
    assertQualification(request, testQualification);
  }
}
