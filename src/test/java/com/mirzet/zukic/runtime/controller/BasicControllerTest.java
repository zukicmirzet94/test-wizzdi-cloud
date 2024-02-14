package com.mirzet.zukic.runtime.controller;

import com.mirzet.zukic.runtime.AppInit;
import com.mirzet.zukic.runtime.model.Basic;
import com.mirzet.zukic.runtime.request.BasicCreate;
import com.mirzet.zukic.runtime.request.BasicFilter;
import com.mirzet.zukic.runtime.request.BasicUpdate;
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
public class BasicControllerTest {

  private Basic testBasic;
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
  public void testBasicCreate() {
    BasicCreate request = new BasicCreate();

    request.setCreationDate(OffsetDateTime.now());

    request.setName("test-string");

    request.setUpdateDate(OffsetDateTime.now());

    request.setDescription("test-string");

    ResponseEntity<Basic> response =
        this.restTemplate.postForEntity("/Basic/createBasic", request, Basic.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testBasic = response.getBody();
    assertBasic(request, testBasic);
  }

  @Test
  @Order(2)
  public void testListAllBasics() {
    BasicFilter request = new BasicFilter();
    ParameterizedTypeReference<PaginationResponse<Basic>> t = new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Basic>> response =
        this.restTemplate.exchange(
            "/Basic/getAllBasics", HttpMethod.POST, new HttpEntity<>(request), t);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Basic> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Basic> Basics = body.getList();
    Assertions.assertNotEquals(0, Basics.size());
    Assertions.assertTrue(Basics.stream().anyMatch(f -> f.getId().equals(testBasic.getId())));
  }

  public void assertBasic(BasicCreate request, Basic testBasic) {
    Assertions.assertNotNull(testBasic);

    if (request.getCreationDate() != null) {
      Assertions.assertEquals(
          request.getCreationDate().atZoneSameInstant(ZoneId.systemDefault()),
          testBasic.getCreationDate().atZoneSameInstant(ZoneId.systemDefault()));
    }

    if (request.getName() != null) {
      Assertions.assertEquals(request.getName(), testBasic.getName());
    }

    if (request.getUpdateDate() != null) {
      Assertions.assertEquals(
          request.getUpdateDate().atZoneSameInstant(ZoneId.systemDefault()),
          testBasic.getUpdateDate().atZoneSameInstant(ZoneId.systemDefault()));
    }

    if (request.getDescription() != null) {
      Assertions.assertEquals(request.getDescription(), testBasic.getDescription());
    }
  }

  @Test
  @Order(3)
  public void testBasicUpdate() {
    BasicUpdate request = new BasicUpdate().setId(testBasic.getId());
    ResponseEntity<Basic> response =
        this.restTemplate.exchange(
            "/Basic/updateBasic", HttpMethod.PUT, new HttpEntity<>(request), Basic.class);
    Assertions.assertEquals(200, response.getStatusCodeValue());
    testBasic = response.getBody();
    assertBasic(request, testBasic);
  }
}
