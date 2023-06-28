package com.mytech.order.dao.base;

import com.mytech.order.dao.base.model.QueryResponse;
import com.mytech.order.exception.CustomerOrderProblemType;
import com.mytech.order.exception.CustomerOrderRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Venky Somisetty
 */
@RequiredArgsConstructor
@Slf4j
public class DefaultDataExtractor<T> implements ResultSetExtractor<List<T>> {

  private static final Logger LOG = LoggerFactory.getLogger(DefaultDataExtractor.class);
  private final Map<String, String> outputParamMapping;
  private final Class<T> pojoType;
  // private final AssertTrackingDataCacheHolder assertTrackingDataCacheHolder;

  public List<T> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
    return resultSetToArrayList(resultSet);
  }

  /**
   * This is responsible convert the ResultSet into a List with Name, Value pairs
   *
   * @param rs
   * @return List of specific class
   * @throws SQLException
   */
  public List<T> resultSetToArrayList(ResultSet rs) throws SQLException {
    long startTime = System.currentTimeMillis();
    ResultSetMetaData md = rs.getMetaData();
    List<T> data = new ArrayList<>();
    if (md != null) {
      int columns = md.getColumnCount();
      boolean isEmpty = true;
      while (rs.next()) {
        try {
          T row = pojoType.newInstance();
          QueryResponse queryResponse = (QueryResponse) row;
          isEmpty = false;
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
              queryResponse.addFieldModel(outputParamMapping.get(columnName), columnValue);
            } else {
              log.debug("Unmapped Field {}", columnName);
            }
          }
          data.add(row);
        } catch (Exception e) {
          log.error("Error Occurred while building the Response", e);
          throw new CustomerOrderRuntimeException(
              CustomerOrderProblemType.DB_DML_ERROR, e.getMessage());
        }
      }
      if (isEmpty) {
        LOG.info("Empty Result ******** ");
      }
    }
    log.info("Time Taken For Transformation " + (System.currentTimeMillis() - startTime));

    return data;
  }
}
