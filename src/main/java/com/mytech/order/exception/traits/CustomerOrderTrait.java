package com.mytech.order.exception.traits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mytech.order.exception.CustomerOrderProblem;
import com.mytech.order.exception.CustomerOrderProblemType;
import com.mytech.order.exception.CustomerOrderRuntimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.bind.ValidationException;

/**
 * @author SOMISV
 */
public interface CustomerOrderTrait extends GeneralProblemTrait {

  @ExceptionHandler(JsonProcessingException.class)
  default ResponseEntity<CustomerOrderProblem> handleJsonProcessingException(
      JsonProcessingException ex) throws JsonProcessingException {
    return Traits.responseEntity(ex, CustomerOrderProblemType.BAD_REQUEST_PROBLEM);
  }

  @ExceptionHandler(ValidationException.class)
  default ResponseEntity<CustomerOrderProblem> handleValidationException(ValidationException ex)
      throws JsonProcessingException {
    String errorDetail = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
    return Traits.responseEntity(ex, CustomerOrderProblemType.BAD_REQUEST_PROBLEM, errorDetail);
  }

  /**
   * To convert the  Error Specific Exception
   *
   * @param ex
   * @return
   * @throws JsonProcessingException
   */
  @ExceptionHandler(CustomerOrderRuntimeException.class)
  default ResponseEntity<CustomerOrderProblem> handleBusinessException(
      CustomerOrderRuntimeException ex) throws JsonProcessingException {
    CustomerOrderProblem orderTrackingProblem = CustomerOrderProblemType.UNKNOWN_ERROR;
    if (ex != null) {
      orderTrackingProblem = ex.getCustomerOrderProblem();
    }
    return Traits.responseEntity(ex, orderTrackingProblem);
  }
}
