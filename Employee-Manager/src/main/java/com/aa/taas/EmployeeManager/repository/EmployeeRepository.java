package com.aa.taas.EmployeeManager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aa.taas.EmployeeManager.domain.Employee;

/**
 * Created by Faust on 1/28/2018.
 */
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
