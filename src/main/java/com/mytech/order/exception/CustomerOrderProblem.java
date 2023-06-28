package com.mytech.order.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SOMISV
 */
@Getter
@Setter
public class CustomerOrderProblem {

  private final String condition;
  private final int status;
  private final String type;
  private final String title;

  public void setDetail(String detail) {
    this.detail = detail;
  }

  private String detail;

  public CustomerOrderProblem(String condition, int code, String type, String title) {
    this.condition = condition;
    this.status = code;
    this.type = type;
    this.title = title;
  }

  public CustomerOrderProblem(
      String condition, int code, String type, String title, String detail) {
    this.condition = condition;
    this.status = code;
    this.type = type;
    this.title = title;
    this.detail = detail;
  }
}
