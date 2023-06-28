package com.mytech.order.dao.base.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created this model to make sure we use CASE INSENSITIVE Map, if not data won't be loaded if the
 * input Tag Names are in different Case
 *
 * @author vsomis956
 */
public class RowModel {

  private Map<String, Object> row = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
  private boolean isDataFound = false;

  public boolean isDataFound() {
    return isDataFound;
  }

  public void setDataFound(boolean isDataFound) {
    this.isDataFound = isDataFound;
  }

  private String rowId;

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public RowModel() {
    rowId = UUID.randomUUID().toString();
  }

  public RowModel(Map<String, Object> row) {
    this.row = row;
  }

  public void put(final String key, Object value) {
    row.put(key, value);
  }

  public Object get(final String key) {
    return row.get(key);
  }

  public Boolean contains(String key) {
    return row.containsKey(key);
  }

  public Map<String, Object> getRow() {
    return row;
  }

  @Override
  public String toString() {
    return "RowModel{" + "row=" + row + '}';
  }
}
