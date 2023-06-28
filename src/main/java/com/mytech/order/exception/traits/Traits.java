package com.mytech.order.exception.traits;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytech.order.exception.CustomerOrderProblem;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author SOMISV
 */
@Slf4j
public class Traits {
  private static ObjectMapper objectMapper = new ObjectMapper();

  private Traits() {}

  public static ResponseEntity<CustomerOrderProblem> responseEntity(
      Exception exception, CustomerOrderProblem problem) {
    return responseEntity(exception, problem, null);
  }

  public static ResponseEntity<CustomerOrderProblem> responseEntity(
          Exception exception, CustomerOrderProblem problem, String detail) {
    problem.setDetail(detail == null ? exception.getMessage() : detail);
    return logAndReturnResponseEntity(problem.getStatus(), problem);
  }

  @SneakyThrows
  private static ResponseEntity<CustomerOrderProblem> logAndReturnResponseEntity(
      int statusCode, CustomerOrderProblem body) {
    ResponseEntity<CustomerOrderProblem> responseEntity =
        ResponseEntity.status(statusCode)
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(body);

    log.error("Request Response - {}", objectMapper.writeValueAsString(responseEntity.getBody()));
    return responseEntity;
  }
}
