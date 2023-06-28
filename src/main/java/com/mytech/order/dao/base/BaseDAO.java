package com.mytech.order.dao.base;

import com.google.common.collect.Maps;
import com.mytech.order.dao.base.config.DBConfigVo;
import com.mytech.order.dao.base.config.DbConfigHolder;
import com.mytech.order.dao.base.model.InnerQuery;
import com.mytech.order.dao.base.model.QueryResponse;
import com.mytech.order.exception.CustomerOrderProblemType;
import com.mytech.order.exception.CustomerOrderRuntimeException;
import com.mytech.order.vo.PaginationVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.*;

/**
 * @author Venky Somisetty
 */
@Slf4j
public abstract class BaseDAO {

  protected JdbcTemplate jdbcTemplate;
  @Autowired protected DbConfigHolder dbConfigHolder;
  private static final String WHERE = " Where ";
  private static final String AND = " And ";
  private static final String IN = " in (";
  private static final String CLOSE = " )";
  private static final String QUOTE = "'";
  protected static final String SITE_ID = "siteId";
  private static final String RESULT = "result";
  private static final String SPACE = " ";

  /**
   * This method is go fetch the required data from Database and map the results with appropriate
   * field
   *
   * @param pojoType
   * @param dbConfigVo
   * @param filterMap
   * @param <T>
   * @return
   */
  public <T> List<T> fetchData(
      Class<T> pojoType, DBConfigVo dbConfigVo, Map<String, Object> filterMap) {
    return fetchData(pojoType, dbConfigVo, filterMap, null);
  }

  /**
   * This method is go fetch the required data from Database and map the results with appropriate
   * field
   *
   * @param pojoType
   * @param dbConfigVo
   * @param filterMap
   * @param <T>
   * @return
   */
  public <T> List<T> fetchData(
      Class<T> pojoType,
      DBConfigVo dbConfigVo,
      Map<String, Object> filterMap,
      PaginationVO paginationVO) {
    // Replace the Filter Params
    String queryWithFilter = getQueryWithFilterTemplate(dbConfigVo.getQuery(), filterMap);
    String orderBy = dbConfigVo.getOrderBy();

    if (StringUtils.isNotBlank(orderBy)) {
      queryWithFilter = queryWithFilter + SPACE + orderBy;
    } else if (paginationVO != null) {
      // Order by is mandatory for Pagination
      orderBy = " ORDER BY (SELECT NULL) ";
      queryWithFilter = queryWithFilter + SPACE + orderBy;
    }

    if (paginationVO != null
        && paginationVO.getPageNo() != null
        && paginationVO.getPageSize() != null) {
      queryWithFilter = queryWithFilter + SPACE + buildPagination(dbConfigVo, paginationVO);
    }

    log.debug("queryWithFilter  " + queryWithFilter);
    long startTime = System.currentTimeMillis();
    this.jdbcTemplate.setFetchSize(1000);
    List<T> map =
        this.jdbcTemplate.query(
            queryWithFilter,
            new DefaultDataExtractor<>(dbConfigVo.getOutputParamMapping(), pojoType));
    log.info("Time Taken For query " + (System.currentTimeMillis() - startTime));
    return map;
  }

  private String buildPagination(DBConfigVo dbConfigVo, PaginationVO paginationVO) {
    if (paginationVO != null) {
      int offSet = (paginationVO.getPageNo() - 1) * paginationVO.getPageSize();
      return "  OFFSET " + offSet + " ROWS FETCH NEXT " + paginationVO.getPageSize() + " ROWS ONLY";
    }

    return StringUtils.EMPTY;
  }

  protected String getQueryWithFilterTemplate(String query, Map<String, Object> filterMap) {

    for (Map.Entry<String, Object> entry : filterMap.entrySet()) {
      query = query.replaceAll(entry.getKey(), (String) entry.getValue());
    }

    return query;
  }


  protected String getQueryWithFilter(String query, Map<String, Object> filterMap) {
    if (filterMap == null) {
      return query;
    }
    StringBuilder queryWithFilters = new StringBuilder(query);
    for (Map.Entry<String, Object> entry : filterMap.entrySet()) {
      if (entry != null && entry.getValue() != null) {
        if ((queryWithFilters
                .toString()
                .toUpperCase(Locale.ROOT)
                .indexOf(WHERE.trim().toUpperCase(Locale.ROOT))
            != -1)) {
          queryWithFilters.append(AND);
        } else {
          queryWithFilters.append(WHERE);
        }
        queryWithFilters.append(entry.getKey().toUpperCase(Locale.ROOT));
        queryWithFilters.append(IN);
        Object value = entry.getValue();
        if (value instanceof String) {
          String stringValue = (String) value;
          if (!(stringValue.startsWith(QUOTE)) && !(stringValue.endsWith(QUOTE))) {
            queryWithFilters.append(QUOTE).append(stringValue).append(QUOTE);
          } else {
            queryWithFilters.append(stringValue);
          }
        } else if (value instanceof InnerQuery) {
          InnerQuery innerQuery = (InnerQuery) value;
          queryWithFilters.append(innerQuery.getQuery());
        } else {
          queryWithFilters.append(entry.getValue());
        }
        queryWithFilters.append(CLOSE);
      }
    }
    return queryWithFilters.toString();
  }

  public List<QueryResponse> performStoreProducer(Object inputVO, DBConfigVo dbConfigVo) {
    return performStoreProducer(null, inputVO, dbConfigVo);
  }

  /**
   * This is common method that could execute any given Store Procedure call
   *
   * @param inputVO
   * @return
   */
  public <T> List<T> performStoreProducer(
      Class<T> pojoType, Object inputVO, DBConfigVo dbConfigVo) {
    SimpleJdbcCall procedureActor =
        new SimpleJdbcCall(jdbcTemplate)
            .withSchemaName(dbConfigVo.getSchema())
            .withProcedureName(dbConfigVo.getProcedureName());
    Map<String, Object> parameters = new HashMap<>(3);
    List<T> data = null;

    if (dbConfigVo.getInputParamMapping() != null && !dbConfigVo.getInputParamMapping().isEmpty()) {
      dbConfigVo
          .getInputParamMapping()
          .entrySet()
          .forEach(
              e1 -> {
                try {
                  Object object = PropertyUtils.getProperty(inputVO, e1.getKey());
                  Class<?> propertyType = PropertyUtils.getPropertyType(inputVO, e1.getKey());
                  if (String.class == propertyType) {
                    procedureActor.addDeclaredParameter(
                        new SqlParameter(e1.getValue(), Types.VARCHAR));
                  } else if (Integer.class == propertyType) {
                    procedureActor.addDeclaredParameter(
                        new SqlParameter(e1.getValue(), Types.INTEGER));
                  } else if (Timestamp.class == propertyType) {
                    procedureActor.addDeclaredParameter(
                        new SqlParameter(e1.getValue(), Types.DATE));
                  }
                  parameters.put(e1.getValue(), object);
                } catch (IllegalAccessException
                    | InvocationTargetException
                    | NoSuchMethodException e) {
                  log.error("Error Occurred while calling Stored Procedure ", e);
                  throw new CustomerOrderRuntimeException(
                      CustomerOrderProblemType.DB_SP_ERROR, e.getMessage());
                }
              });
    }

    procedureActor.returningResultSet(
        RESULT, new BaseRowMapper<>(dbConfigVo.getOutputParamMapping(), pojoType));
    Map<String, Object> out = procedureActor.execute(parameters);

    data = (List) out.get(RESULT);
    return data;
  }

  public void performDML(Object paramObj, DBConfigVo dbConfigVo) {
    List parameters = new ArrayList();
    dbConfigVo
        .getInputParamMapping()
        .entrySet()
        .forEach(
            e1 -> {
              try {
                Object object = PropertyUtils.getProperty(paramObj, e1.getValue());
                parameters.add(object);
              } catch (IllegalAccessException
                  | InvocationTargetException
                  | NoSuchMethodException e) {
                log.error("Error Occurred while calling DML statement ", e);
                throw new CustomerOrderRuntimeException(
                    CustomerOrderProblemType.DB_DML_ERROR, e.getMessage());
              }
            });
    jdbcTemplate.update(dbConfigVo.getQuery(), parameters.toArray());
  }

  /**
   * This is to build the Filter String
   *
   * @param dbConfigVo
   * @param filterData
   * @return
   */
  protected Map<String, Object> buildDbFieldFilterMap(
      DBConfigVo dbConfigVo, Map<String, Object> filterData) {
    if (filterData == null) {
      return Maps.newHashMap();
    }
    Map<String, Object> physicalFilerData = new HashMap<>();
    if (dbConfigVo.getInputParamMapping() != null) {
      dbConfigVo
          .getInputParamMapping()
          .entrySet()
          .forEach(
              e1 -> {
                physicalFilerData.put(e1.getKey(), filterData.get(e1.getValue()));
              });
      log.info("physical FilerData " + physicalFilerData);
    }
    return physicalFilerData;
  }
}
