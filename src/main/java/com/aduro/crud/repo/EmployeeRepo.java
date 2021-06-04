package com.aduro.crud.repo;

import org.springframework.data.repository.CrudRepository;

import com.aduro.crud.entity.Employee;

public interface EmployeeRepo extends CrudRepository<Employee, Long> {
	
}
