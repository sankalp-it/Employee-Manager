package com.aa.taas.EmployeeManager.service;

import com.aa.taas.EmployeeManager.domain.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Created by Faust on 1/28/2018.
 */
public interface EmployeeService {

    Employee saveEmployee(Employee e);

    Employee findByEmployeeId(String employeeId);

    void deleteByEmployeeId(String employeeId);

    void updateEmployee(Employee e);

    boolean employeeExists(Employee e);

    List<Employee> findAll();

    void deleteAll();
}
