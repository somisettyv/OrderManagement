package com.mytech.order.service;

import com.mytech.order.dao.base.OrderManagementDao;
import com.mytech.order.dao.base.config.DBConfigVo;
import com.mytech.order.dao.base.config.DbConfigHolder;
import com.mytech.order.exception.CustomerOrderProblemType;
import com.mytech.order.exception.CustomerOrderRuntimeException;
import com.mytech.order.vo.OrderSummaryRequestVO;
import com.mytech.order.vo.OrderSummaryResponseVO;
import com.mytech.order.vo.OrderVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderManagementServiceImpl implements OrderManagementService{


    private final OrderManagementDao orderManagementDao;

    private final DbConfigHolder dbConfigHolder;

    public OrderManagementServiceImpl(OrderManagementDao orderManagementDao, DbConfigHolder dbConfigHolder) {
        this.orderManagementDao = orderManagementDao;
        this.dbConfigHolder = dbConfigHolder;
    }

    public OrderSummaryResponseVO getOrder(final OrderSummaryRequestVO orderSummaryRequestVO){
        DBConfigVo dbConfig = dbConfigHolder.getDbConfig("Get_Order_Summary");

        if(StringUtils.isBlank((String) orderSummaryRequestVO.getFilters().get("orderStartDate"))){
            throw new CustomerOrderRuntimeException(CustomerOrderProblemType.BAD_REQUEST_PROBLEM, "orderStartDate should not be empty");
        }
        if(StringUtils.isBlank((String) orderSummaryRequestVO.getFilters().get("orderEndDate"))){
            throw new CustomerOrderRuntimeException(CustomerOrderProblemType.BAD_REQUEST_PROBLEM, "orderEndDate should not be empty");
        }

        if(StringUtils.isBlank((String) orderSummaryRequestVO.getFilters().get("customerId"))){
            throw new CustomerOrderRuntimeException(CustomerOrderProblemType.BAD_REQUEST_PROBLEM, "customerId should not be empty");
        }

        List<OrderVO> orderVOList = orderManagementDao.getOrderSummaryByCustomer(orderSummaryRequestVO, dbConfig);
        if(CollectionUtils.isEmpty(orderVOList)){
            throw new CustomerOrderRuntimeException(CustomerOrderProblemType.RESOURCE_NOT_FOUND_PROBLEM,"No Order Data Found");
        }
        OrderSummaryResponseVO orderSummaryResponseVO = new OrderSummaryResponseVO(3,orderVOList);
        return orderSummaryResponseVO;
    }
}
