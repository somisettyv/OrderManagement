package com.mytech.order.repository;

import com.mytech.order.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    Department findByDepNo(long deptId);
}
