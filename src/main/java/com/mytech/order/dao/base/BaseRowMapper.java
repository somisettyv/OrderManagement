package com.mytech.order.dao.base;

import com.mytech.order.dao.base.model.QueryResponse;
import com.mytech.order.exception.CustomerOrderProblemType;
import com.mytech.order.exception.CustomerOrderRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Venky Somisetty
 *     <p>This class is responsible for extracting data for each Records
 * @param <T>
 */
@RequiredArgsConstructor
@Slf4j
public class BaseRowMapper<T> implements RowMapper<T> {

  private final Map<String, String> outputParamMapping;
  private final Class<T> pojoType;
  // private final AssertTrackingDataCacheHolder assertTrackingDataCacheHolder;

  @Override
  public T mapRow(ResultSet rs, int rowNum) throws SQLException {
    try {
      ResultSetMetaData md = rs.getMetaData();
      int columns = md.getColumnCount();
      T row = pojoType.newInstance();
      for (int i = 1; i <= columns; ++i) {
        String columnName = md.getColumnName(i);
        Object columnValue = rs.getObject(md.getColumnName(i));
        if (md.getColumnType(i) == 93) {
          if (columnValue != null) {
            // columnValue = convertToUtc((Timestamp) columnValue);
          }
        }
        String targetColumnName = outputParamMapping.get(columnName);
        if (StringUtils.isNotBlank(targetColumnName)) {
          ((QueryResponse) row).addFieldModel(outputParamMapping.get(columnName), columnValue);
        } else {
          log.info("Unmapped Field {}", columnName);
        }
      }
      return row;
    } catch (Exception e) {
      log.error("Error Occurred while building the Response", e);
      throw new CustomerOrderRuntimeException(
          CustomerOrderProblemType.DB_DML_ERROR, e.getMessage());
    }
  }
}
