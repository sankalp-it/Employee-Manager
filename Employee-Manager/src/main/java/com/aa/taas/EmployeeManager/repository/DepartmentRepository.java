package com.aa.taas.EmployeeManager.repository;

import com.aa.taas.EmployeeManager.domain.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Faust on 1/28/2018.
 */
public interface DepartmentRepository extends MongoRepository<Department,String>{
}
