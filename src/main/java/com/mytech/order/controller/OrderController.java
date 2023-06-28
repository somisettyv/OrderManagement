package com.mytech.order.controller;

import com.mytech.order.service.OrderManagementService;
import com.mytech.order.vo.OrderSummaryRequestVO;
import com.mytech.order.vo.OrderSummaryResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {

    @Autowired
    private OrderManagementService orderManagementService;

    @RequestMapping(
            value = "/myorg/order/{customerId}",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderSummaryResponseVO> getOrdersByCustomer(@PathVariable String customerId,
                                                                  @RequestBody OrderSummaryRequestVO orderSummaryRequestVO) {

        OrderSummaryResponseVO orderSummaryResponseVO = orderManagementService.getOrder(orderSummaryRequestVO);
        return new ResponseEntity<OrderSummaryResponseVO>(orderSummaryResponseVO, HttpStatus.OK);
    }
}
