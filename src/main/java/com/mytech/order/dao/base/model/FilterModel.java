package com.mytech.order.dao.base.model;

public class FilterModel {

  private String logicalName;
  private String physicalName;
  private String value;

  public String getLogicalName() {
    return logicalName;
  }

  public void setLogicalName(String logicalName) {
    this.logicalName = logicalName;
  }

  public String getPhysicalName() {
    return physicalName;
  }

  public void setPhysicalName(String physicalName) {
    this.physicalName = physicalName;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
