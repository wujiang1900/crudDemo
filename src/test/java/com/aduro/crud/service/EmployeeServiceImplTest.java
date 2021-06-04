package com.aduro.crud.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.aduro.crud.dto.EmployeeDto;
import com.aduro.crud.entity.Employee;
import com.aduro.crud.exception.ResourceNotFoundException;
import com.aduro.crud.repo.EmployeeRepo;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {
	
	@Mock
	private EmployeeRepo repo;
	
	private ModelMapper mapper = new ModelMapper();
	
	@InjectMocks
	EmployeeService service = new EmployeeServiceImpl(repo);
	
	private Employee emp;
	private long id;
	private String name;
	private List<Employee> empList;
	
	@Before
	public void setup() {
		id = 5;
		emp = new Employee();
		emp.setId(id);
		emp.setName(name);
		empList = new ArrayList<>();
		empList.add(emp);
	}
	
	@Test
	public void getEmployeeList() {
		when(repo.findAll()).thenReturn(empList);
		
		List<EmployeeDto> actual = service.getEmployeeList();
		assertEquals(1, actual.size());
		assertEquals(id, actual.get(0).getId());
	}
	
	@Test
	public void getEmployeeById_found() throws ResourceNotFoundException {
		when(repo.findById(id)).thenReturn(Optional.of(emp));
		EmployeeDto actual = service.getEmployeeById(id);
		assertEquals(name, actual.getName());
		assertEquals(id, actual.getId());
	}	
	@Test (expected = ResourceNotFoundException.class)
	public void getEmployeeById_notfound() throws ResourceNotFoundException {
		when(repo.findById(id)).thenReturn(Optional.empty());
		service.getEmployeeById(id);
	}
	
	@Test
	public void createEmployee_() {
		when(repo.save(emp)).thenReturn(emp);
		EmployeeDto dto = mapper.map(emp, EmployeeDto.class);
		assertEquals(id, service.createEmployee(dto));
	}	
	@Test (expected = IllegalArgumentException.class)
	public void createEmployee_null_dto() {
		service.createEmployee(null);
	}
	
	@Test
	public void updateEmployee_valid_id() {
		when(repo.findById(id)).thenReturn(Optional.of(emp));
		EmployeeDto dto = mapper.map(emp, EmployeeDto.class);
		service.updateEmployee(dto);
		verify(repo).save(emp);
	}		
	@Test (expected = IllegalArgumentException.class)
	public void updateEmployee_invalid_id() {
		when(repo.findById(id)).thenReturn(Optional.empty());
		EmployeeDto dto = mapper.map(emp, EmployeeDto.class);
		service.updateEmployee(dto);
		verify(repo.save(emp), never());
	}
	@Test (expected = IllegalArgumentException.class)
	public void updateEmployee_null_dto() {
		service.updateEmployee(null);
	}
	
	@Test
	public void deleteEmployee() {
		service.deleteEmployee(id);
		verify(repo).deleteById(id);
	}
}
