package com.mytech.order.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Department {

  // Generate Ids using Database Sequence

  // This would have one-to-many relation with employees
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "department")
  List<Employee> employees;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dept_Sequence")
  @SequenceGenerator(name = "dept_Sequence", sequenceName = "DEP_SEQ", allocationSize = 1)
  @Column(name = "depno")
  private Long depNo;
  @Column(name = "depname")
  private String name;
  @Column(name = "deploc")
  private String location;
}
