package com.aduro.crud.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aduro.crud.dto.EmployeeDto;
import com.aduro.crud.exception.ResourceNotFoundException;
import com.aduro.crud.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	private EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}
	
	@GetMapping
	public Iterable<EmployeeDto> getEmployeeList(){
		return service.getEmployeeList();
	}	
	
	@GetMapping("/{id}")
	public EmployeeDto getEmployeeById(@PathVariable long id) throws ResourceNotFoundException{
		validateId(id);
		return service.getEmployeeById(id);		
	}
	
	private void validateId(long id) {
		if(id<=0) 
			throw new IllegalArgumentException("Invalid Employee id: "+id);
	}

	@PostMapping
	public long createEmployee(@RequestBody @Valid EmployeeDto emp){
		return service.createEmployee(emp);
	}	
	
	@PutMapping("/{id}")
	public void updateEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto emp){
		validateId(id);
		if(emp.getId()<=0) {
			emp.setId(id); 
		}
		else if(id != emp.getId()) {
			throw new IllegalArgumentException("Id ("+id+") in uri does not match id ("+emp.getId()+") in request body.");
		}
			
		service.updateEmployee(emp);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id){
		validateId(id);
		service.deleteEmployee(id);
	}
}
