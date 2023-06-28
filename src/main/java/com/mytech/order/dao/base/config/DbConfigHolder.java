package com.mytech.order.dao.base.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytech.order.exception.CustomerOrderProblemType;
import com.mytech.order.exception.CustomerOrderRuntimeException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Configuration
@Getter
@Slf4j
public class DbConfigHolder {

  public static final String order_STATUS_DB_CONFIG_PATH = "/orderTrackingDBConfig.json";
  private Map<String, List<DBConfigVo>> allDbMappings = new HashMap<String, List<DBConfigVo>>();
  private ObjectMapper objectMapper = new ObjectMapper();

  @PostConstruct
  private void loadDBConfigs() throws IOException {
    loadJsonToMap(order_STATUS_DB_CONFIG_PATH);
  }

  private void loadJsonToMap(String fileName) throws IOException {
    InputStream inputStream = DbConfigHolder.class.getResourceAsStream(fileName);
    List<DBConfigVo> dbConfigVoList =
            objectMapper.readValue(inputStream, new TypeReference<List<DBConfigVo>>() {
            });
    allDbMappings = dbConfigVoList.stream().collect(groupingBy(DBConfigVo::getConfigurationName));
    log.info("Loading {} number of Configurations ", allDbMappings.size());
  }

  public DBConfigVo getDbConfig(String configName) {
    List<DBConfigVo> dbConfigVoList = allDbMappings.get(configName);
    if (CollectionUtils.isNotEmpty(dbConfigVoList)) {
      return dbConfigVoList.get(0);
    }
    throw new CustomerOrderRuntimeException(
            CustomerOrderProblemType.DB_CONFIG_ERROR,
            CustomerOrderProblemType.DB_CONFIG_ERROR.getDetail());
  }
}
