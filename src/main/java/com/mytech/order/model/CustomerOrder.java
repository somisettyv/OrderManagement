package com.mytech.order.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class CustomerOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_Sequence")
  @SequenceGenerator(name = "Ordr_Sequence", sequenceName = "Order_Seq", allocationSize = 1)
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
