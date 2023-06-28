package com.mytech.order.repository;

import com.mytech.order.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

  List<Employee> findByDepno(long teamId);

  Optional<Employee> findByNumber(long employeeNumber);
}
