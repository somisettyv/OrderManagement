package com.mytech.order.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class CustomerOrderHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_Sequence_his")
  @SequenceGenerator(
      name = "order_Sequence_his",
      sequenceName = "ORDER_SEQ_His",
      allocationSize = 1)
  @Column(name = "orderId")
  private Long orderId;

  @Column(name = "customerId")
  private Long customerId;

  @Column(name = "startdate")
  private Date startdate;

  @Column(name = "enddate")
  private Date enddate;

  @Column(name = "mgr")
  private String managerName;

  @Column(name = "orderAmount")
  private Double orderAmount;
}
