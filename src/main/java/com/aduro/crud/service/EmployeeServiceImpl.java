package com.aduro.crud.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aduro.crud.dto.EmployeeDto;
import com.aduro.crud.entity.Employee;
import com.aduro.crud.exception.ResourceNotFoundException;
import com.aduro.crud.repo.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepo repo;
	private ModelMapper mapper;
	
	public EmployeeServiceImpl(EmployeeRepo repo) {
		this.repo = repo;
		mapper = new ModelMapper();
	}
	
	@Override
	public List<EmployeeDto> getEmployeeList() {
		return StreamSupport.stream(repo.findAll().spliterator(), false)
				.map(e->mapper.map(e, EmployeeDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public EmployeeDto getEmployeeById(long id) throws ResourceNotFoundException {
		Optional<Employee> empOpt = repo.findById(id);
		if(empOpt.isPresent())
			return mapper.map(empOpt.get(), EmployeeDto.class);
		throw new ResourceNotFoundException("Invalid Employee id: "+id);
	}

	@Override
	public long createEmployee(EmployeeDto emp) {
		checkNull(emp);
		Employee e = repo.save(mapper.map(emp, Employee.class));
		return e.getId();
	}

	private void checkNull(EmployeeDto emp) {
		if(emp == null) 
			throw new IllegalArgumentException("Null employee request");
	}

	@Override
	public void updateEmployee(EmployeeDto emp) {
		checkNull(emp);
		if(repo.findById(emp.getId()).isPresent()) {
			repo.save(mapper.map(emp, Employee.class));
			return;
		}
		
		throw new IllegalArgumentException("Invalid Employee id: "+emp.getId());
	}

	@Override
	public void deleteEmployee(long id) {
		repo.deleteById(id);
	}
}
