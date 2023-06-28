/**
 * 
 */
package com.mytech.order.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SOMISV
 *
 */
@Getter
@Setter
public class OrderSummaryRequestVO implements Serializable{

	private static final long serialVersionUID = 1L;

    private PaginationVO pagination = new PaginationVO();

	public Map<String, Object> filters = new HashMap<>();

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

}
