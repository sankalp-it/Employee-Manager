package com.aa.taas.EmployeeManager.service.impl;

import com.aa.taas.EmployeeManager.domain.Employee;
import com.aa.taas.EmployeeManager.repository.EmployeeRepository;
import com.aa.taas.EmployeeManager.repository.factory.EmployeeRepositoryFactory;
import com.aa.taas.EmployeeManager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Faust on 1/28/2018.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    //@Qualifier("mongodb")
    private EmployeeRepository employeeRepository;
    
    @Value("${database.type}")
    private String databaseType;
    
    @Override
    public Employee saveEmployee(Employee e) {
    	
        return employeeRepository.save(e);
    }

    @Override
    public Employee findByEmployeeId(String employeeId) {
        return employeeRepository.findOne(employeeId);
    }

    @Override
    public void deleteByEmployeeId(String employeeId) {
        employeeRepository.delete(employeeId);
    }

    @Override
    public void updateEmployee(Employee e) {
        employeeRepository.save(e);
    }

    @Override
    public boolean employeeExists(Employee e) {
        return employeeRepository.exists(Example.of(e));
    }

    @Override
    public List<Employee> findAll() {
    	System.out.println("The Database type used :"+ databaseType);
        return employeeRepository.findAll();
    }

    @Override
    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
