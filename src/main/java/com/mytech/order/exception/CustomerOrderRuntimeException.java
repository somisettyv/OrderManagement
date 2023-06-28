package com.mytech.order.exception;

import java.util.Map;

/**
 * @author SOMISV
 */
public class CustomerOrderRuntimeException extends RuntimeException {
  private final CustomerOrderProblem customerOrderProblem;
  private final String detail;
  private Map<String, Object> additionalParameters;

  public CustomerOrderRuntimeException(String detail) {
    super(detail);
    this.detail = detail;
    this.customerOrderProblem = CustomerOrderProblemType.INTERNAL_ERROR_PROBLEM;
    this.additionalParameters = null;
  }

  public CustomerOrderRuntimeException(String detail, Throwable ex) {
    super(ex.getMessage());
    this.detail = ex.getMessage();
    this.customerOrderProblem = CustomerOrderProblemType.INTERNAL_ERROR_PROBLEM;
  }

  public CustomerOrderRuntimeException(CustomerOrderProblem orderTrackingProblem, String detail) {
    super(detail);
    this.detail = detail;
    this.customerOrderProblem = orderTrackingProblem;
  }

  public CustomerOrderRuntimeException(
      CustomerOrderProblem orderTrackingProblem,
      String detail,
      Map<String, Object> additionalParameters) {
    super(detail);
    this.detail = detail;
    this.customerOrderProblem = orderTrackingProblem;
    this.additionalParameters = additionalParameters;
  }

  public CustomerOrderRuntimeException(
          CustomerOrderProblem orderTrackingProblem, String detail, Throwable ex) {
    super(detail, ex);
    this.detail = detail;
    this.customerOrderProblem = orderTrackingProblem;
  }

  public CustomerOrderRuntimeException(
      CustomerOrderProblem orderTrackingProblem,
      String detail,
      Throwable ex,
      Map<String, Object> additionalParameters) {
    super(detail, ex);
    this.detail = detail;
    this.customerOrderProblem = orderTrackingProblem;
    this.additionalParameters = additionalParameters;
  }

  public CustomerOrderProblem getCustomerOrderProblem() {
    return customerOrderProblem;
  }

  public String getDetail() {
    return detail;
  }
}
