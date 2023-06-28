package com.mytech.order.dao.base.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * @author SOMISV
 *     <p>This is the base query response class which has to be extended by all Query Response
 *     classes
 */
@Slf4j
public abstract class QueryResponse implements Serializable {

  public void addFieldModel(String fieldName, Object actualValue) {
    try {
      if (StringUtils.isNotBlank(fieldName)) {
        PropertyUtils.setProperty(this, fieldName, actualValue);
      }
    } catch (Exception e) {
      log.error("fieldName " + fieldName, e);
    }
  }
}
