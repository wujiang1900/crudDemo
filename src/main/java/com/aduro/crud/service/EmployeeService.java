package com.aduro.crud.service;

import java.util.List;

import com.aduro.crud.dto.EmployeeDto;
import com.aduro.crud.exception.ResourceNotFoundException;

public interface EmployeeService {

	List<EmployeeDto> getEmployeeList();

	EmployeeDto getEmployeeById(long id) throws ResourceNotFoundException;

	long createEmployee(EmployeeDto emp);

	void updateEmployee(EmployeeDto emp);

	void deleteEmployee(long id);
	
}
