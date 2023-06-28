package com.mytech.order.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author SOMISV
 */
public class CustomerOrderProblemType {
  private CustomerOrderProblemType() {}

  private static List<CustomerOrderProblem> values = new ArrayList<>();
  public static final CustomerOrderProblem UNKNOWN_ERROR =
      add(new CustomerOrderProblem("", 500, "order-1001", "Server error"));
  public static final CustomerOrderProblem RESOURCE_NOT_FOUND_PROBLEM =
      add(new CustomerOrderProblem("RESOURCE_NOT_FOUND", 404, "order-1002", "Not Found"));
  public static final CustomerOrderProblem INTERNAL_ERROR_PROBLEM =
      add(new CustomerOrderProblem("INTERNAL_ERROR", 500, "order-1004", "Internal Error"));
  public static final CustomerOrderProblem BAD_REQUEST_PROBLEM =
      add(new CustomerOrderProblem("BAD_REQUEST", 400, "order-1003", "Bad Request"));
  public static final CustomerOrderProblem CONCURRENT_UPDATE_PROBLEM =
      add(
          new CustomerOrderProblem(
              "CONCURRENT_UPDATE_ERROR", 500, "order-1007", "Concurrent Update"));
  public static final CustomerOrderProblem ORDER_STATUS_FILED =
      add(new CustomerOrderProblem("ORDER_STATUS_ERROR", 500, "order-1008", "Versa Error"));
  public static final CustomerOrderProblem UNAUTHORIZED =
      add(new CustomerOrderProblem("UNAUTHORIZED", 401, "SM-1005", "Authentication Exception"));
  public static final CustomerOrderProblem SERVICE_UNAVAILABLE =
      add(
          new CustomerOrderProblem(
              "SERVICE_UNAVAILABLE", 503, "order-1017", "Service Unavailable"));
  public static final CustomerOrderProblem DB_SP_ERROR =
      add(new CustomerOrderProblem("DB_SP_ERROR", 500, "order-1006", "Service Unavailable"));
  public static final CustomerOrderProblem DB_CONFIG_ERROR =
      add(new CustomerOrderProblem("DB_CONFIG_ERROR", 500, "order-1007", "DB Config Unavailable"));
  public static final CustomerOrderProblem SAVE_MANAGED_ORDER =
      add(
          new CustomerOrderProblem(
              "SAVE_MANAGED_ORDER", 500, "order-1008", "Unable to save the Order Data"));
  public static final CustomerOrderProblem DELETE_MANAGED_ORDER =
      add(
          new CustomerOrderProblem(
              "DELETE_MANAGED_ORDER", 500, "order-1009", "Unable to Delete the Order Data"));
  public static final CustomerOrderProblem DB_DML_ERROR =
      add(
          new CustomerOrderProblem(
              "DB_DML_ERROR", 500, "order-1010", "Unable to execute DML statement"));
  public static final CustomerOrderProblem ADD_COMMENT_ERROR =
      add(
          new CustomerOrderProblem(
              "ADD_COMMENT_ERROR", 500, "order-1011", "Unable to Add comment to Order"));
  public static final CustomerOrderProblem GET_HISTORY_ERROR =
      add(
          new CustomerOrderProblem(
              "GET_HISTORY_ERROR", 500, "order-1012", "Unable to get order activity history"));
  public static final CustomerOrderProblem ORDER_VALIDATION_CHECK_ERROR =
      add(
          new CustomerOrderProblem(
              "ORDER_VALIDATION_CHECK_ERROR",
              500,
              "order-1013",
              "Unable to get order information"));
  public static final CustomerOrderProblem RESOURCE_NOT_FOUND_PROBLEM_JIBE_TOKEN =
      add(new CustomerOrderProblem("RESOURCE_NOT_FOUND", 404, "order-1010", "Not Found"));
  public static final CustomerOrderProblem UNAUTHORIZED_JIBE_TOKEN =
      add(new CustomerOrderProblem("UNAUTHORIZED", 401, "SM-1011", "Authentication Exception"));
  public static final CustomerOrderProblem SERVICE_UNAVAILABLE_JIBE_TOKEN =
      add(
          new CustomerOrderProblem(
              "SERVICE_UNAVAILABLE", 503, "order-1012", "Service Unavailable"));
  public static final CustomerOrderProblem BAD_REQUEST_PROBLEM_JIBE_TOKEN =
      add(new CustomerOrderProblem("BAD_REQUEST", 400, "order-1013", "Bad Request"));
  public static final CustomerOrderProblem GET_TAG_LIST_BY_SITE_ERROR =
      add(
          new CustomerOrderProblem(
              "GET_TAG_LIST_BY_SITE_ERROR", 500, "order-1014", "Unable to get tag list by site"));
  public static final CustomerOrderProblem GET_TAG_BY_ID_ERROR =
      add(
          new CustomerOrderProblem(
              "GET_TAG_BY_ID_ERROR", 500, "order-1015", "Unable to get tag by id"));
  public static final CustomerOrderProblem GET_TAG_SUMMARY_ERROR =
      add(
          new CustomerOrderProblem(
              "GET_TAG_SUMMARY_ERROR", 500, "order-1016", "Unable to get tag summary by site"));
  public static final CustomerOrderProblem GET_LOCATION_ERROR =
      add(
          new CustomerOrderProblem(
              "GET_LOCATION_ERROR", 500, "order-1017", "Unable to get location list by site"));
  public static final CustomerOrderProblem EDIT_MANAGED_ORDER =
      add(
          new CustomerOrderProblem(
              "EDIT_MANAGED_ORDER", 500, "order-1018", "Unable to update the Order Data"));
  public static final CustomerOrderProblem GET_SITE_ACTIVITY_ERROR =
      add(
          new CustomerOrderProblem(
              "GET_SITE_ACTIVITY_ERROR", 500, "order-1019", "Unable to get Site Activity"));
  public static final CustomerOrderProblem EXPORT_CONFIG_ERROR =
      add(
          new CustomerOrderProblem(
              "EXPORT_CONFIG_ERROR", 500, "order-1020", "Export Config Unavailable"));
  public static final CustomerOrderProblem EXPORT_ERROR =
      add(
          new CustomerOrderProblem(
              "EXPORT_ERROR", 500, "order-1021", "Unable to complete writing the export file"));
  public static final CustomerOrderProblem UPDATE_SITE_GEOFENCES_ERROR =
      add(
          new CustomerOrderProblem(
              "UPDATE_SITE_GEOFENCES_ERROR", 500, "order-1022", "Unable to update Geofences"));
  public static final CustomerOrderProblem AUTH_VAULT_ERROR =
      add(
          new CustomerOrderProblem(
              "AUTH_VAULT_ERROR",
              500,
              "order-1023",
              "Error Occurred while Refreshing the Security Token"));
  public static final CustomerOrderProblem CREATE_AR_NAVIGATION_ORDER =
      add(
          new CustomerOrderProblem(
              "CREATE_AR_NAVIGATION_ORDER",
              500,
              "order-1008",
              "Unable to create the ArNavigation Deeplink"));
  public static final CustomerOrderProblem LOADING_DATETIME_OFFSET_ERROR =
      add(
          new CustomerOrderProblem(
              "LOADING_DATETIME_OFFSET_ERROR",
              500,
              "order-1023",
              "Unable to load the DateTime Offset"));

  public static List<CustomerOrderProblem> values() {
    return Collections.unmodifiableList(values);
  }

  protected static CustomerOrderProblem add(CustomerOrderProblem responseStatus) {
    values.add(responseStatus);
    return responseStatus;
  }

  public static CustomerOrderProblem fromHttpStatus(int httpStatus) {
    List<CustomerOrderProblem> values = values();
    CustomerOrderProblem responseStatus =
        values.stream()
            .filter(e -> e != null && e.getStatus() == httpStatus)
            .findFirst()
            .orElse(UNKNOWN_ERROR);
    return responseStatus;
  }
}
