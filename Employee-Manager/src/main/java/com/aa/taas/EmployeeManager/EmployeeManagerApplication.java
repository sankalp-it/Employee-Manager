package com.aa.taas.EmployeeManager;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.apache.log4j.Logger;
import com.aa.taas.EmployeeManager.domain.Department;
import com.aa.taas.EmployeeManager.domain.Employee;
import com.aa.taas.EmployeeManager.repository.DepartmentRepository;
import com.aa.taas.EmployeeManager.repository.EmployeeRepository;

@SpringBootApplication
public class EmployeeManagerApplication {
	private static final Logger LOGGER = Logger.getLogger(EmployeeManagerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagerApplication.class, args);
	}
	//@Qualifier("${database.type}") 
	@Bean
	public CommandLineRunner init(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
		return (args) -> {
			employeeRepository.deleteAll();
			departmentRepository.deleteAll();
			Employee e = employeeRepository.save(new Employee("Ion", "Pascari", 23));
			departmentRepository.save(new Department("Service Department", "Service Rocks!", Arrays.asList(e)));

			for (Department d : departmentRepository.findAll()) {
				LOGGER.info("Department: " + d);
			}
		};
	}
}

