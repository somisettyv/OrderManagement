package com.mytech.order.service;

import com.mytech.order.vo.OrderSummaryRequestVO;
import com.mytech.order.vo.OrderSummaryResponseVO;

public interface OrderManagementService {

     OrderSummaryResponseVO getOrder(OrderSummaryRequestVO orderSummaryRequestVO);
}
