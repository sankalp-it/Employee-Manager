package com.aa.taas.EmployeeManager.controller;

import com.aa.taas.EmployeeManager.domain.Employee;
import com.aa.taas.EmployeeManager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Faust on 1/29/2018.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    

    
    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    public HttpEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            employees.forEach(e -> e.add(linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees")));
            employees.forEach(e -> e.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(e.getEmployeeId())).withSelfRel()));
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public HttpEntity<Employee> getEmployeeById(@PathVariable("id") String employeeId) {
        Employee byEmployeeId = employeeService.findByEmployeeId(employeeId);
        if (byEmployeeId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            byEmployeeId.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(byEmployeeId.getEmployeeId())).withSelfRel());
            return new ResponseEntity<>(byEmployeeId, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/employee/", method = RequestMethod.POST)
    public HttpEntity<?> saveEmployee(@RequestBody Employee e) {
	        if (employeeService.employeeExists(e)) {
	            return new ResponseEntity<>(HttpStatus.CONFLICT);
	        } else {
	            Employee employee = employeeService.saveEmployee(e);
	            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/employees/employee/{id}")
                    .buildAndExpand(employee.getEmployeeId()).toUri();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);
            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public HttpEntity<?> updateEmployee(@PathVariable("id") String id, @RequestBody Employee e) {
        Employee byEmployeeId = employeeService.findByEmployeeId(id);
        if(byEmployeeId == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            byEmployeeId.setAge(e.getAge());
            byEmployeeId.setFirstName(e.getFirstName());
            byEmployeeId.setLastName(e.getLastName());
            employeeService.updateEmployee(byEmployeeId);
            byEmployeeId.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(byEmployeeId.getEmployeeId())).withSelfRel());
            return new ResponseEntity<>(byEmployeeId, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") String employeeId) {
        employeeService.deleteByEmployeeId(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/employee/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        employeeService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
