package com.mytech.order.dao.base.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class DBConfigVo {

  @JsonProperty("configurationName")
  private String configurationName;

  @JsonProperty("schema")
  private String schema;

  @JsonProperty("procedureName")
  private String procedureName;

  @JsonProperty("query")
  private String query;

  @JsonProperty("inputParamMapping")
  private Map<String, String> inputParamMapping;

  @JsonProperty("outputParamMapping")
  private Map<String, String> outputParamMapping;

  @JsonProperty("orderBy")
  private String orderBy;

  @JsonProperty("configurationName")
  public String getConfigurationName() {
    return configurationName;
  }
}
