package com.mytech.order.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Employee {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "depno", nullable = false, insertable = false, updatable = false)
  Department department;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_Sequence")
  @SequenceGenerator(name = "emp_Sequence", sequenceName = "EMP_SEQ", allocationSize = 1)
  @Column(name = "empno")
  private Long number;
  @Column(name = "depno")
  private Long depno;
  @Column(name = "ename")
  private String name;
  @Column(name = "job")
  private String jobName;
  @Column(name = "mgr")
  private String managerName;
  @Column(name = "sal")
  private Double salary;
}
