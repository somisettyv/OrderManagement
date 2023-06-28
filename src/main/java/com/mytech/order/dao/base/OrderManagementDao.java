package com.mytech.order.dao.base;

import com.mytech.order.dao.base.config.DBConfigVo;
import com.mytech.order.vo.OrderSummaryRequestVO;
import com.mytech.order.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author SOMISV
 *
 */
@Service
@Slf4j
public class OrderManagementDao extends BaseDAO {

    @Autowired
    public OrderManagementDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * This method is to get the available Categories by the siteId
     * @param dbConfig
     * @return
     */
    public List<OrderVO> getOrderSummaryByCustomer(OrderSummaryRequestVO orderSummaryRequestVO, DBConfigVo dbConfig) {

        Map<String, Object> filterData = orderSummaryRequestVO.getFilters();

        Map<String, Object> filterMap = buildDbFieldFilterMap(dbConfig, filterData);
        return  fetchData(OrderVO.class, dbConfig, filterMap).stream()
				.sorted(Comparator.comparing(OrderVO::getOrderDate))
				.collect(Collectors.toList());
    }

}