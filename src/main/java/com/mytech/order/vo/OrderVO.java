package com.mytech.order.vo;

import com.mytech.order.dao.base.model.QueryResponse;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class OrderVO extends QueryResponse {

    private Integer custId;
    private String custName;
    private Long orderId;
    private Date orderDate;
    private String status;
    private Double orderAmt;

}
