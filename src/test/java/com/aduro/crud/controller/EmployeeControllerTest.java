package com.aduro.crud.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

//import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import org.springframework.web.util.NestedServletException;

import com.aduro.crud.dto.EmployeeDto;
import com.aduro.crud.exception.ResourceNotFoundException;
import com.aduro.crud.service.EmployeeService;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Mock
	private EmployeeService service;

    @InjectMocks
    EmployeeController controller = new EmployeeController(service);

//    @Autowired
    MockMvc mockMvc;
	
	@MockBean(name = "mvcValidator")
	private Validator mockValidator;
	
    private long id = 5;
    private String name = "john doe";
    EmployeeDto emp;

    @BeforeEach
    void setup() {
    	this.mockMvc = MockMvcBuilders.standaloneSetup(controller).setValidator(mockValidator).build();
    	emp = new EmployeeDto();
    	emp.setId(id);
    	emp.setName(name);
    }

    @AfterEach
    void tearDown() {
//        reset(service);
    }
    
    @DisplayName("GET /employees should return employee list")
    @Test
    public void getEmployeeList() throws Exception {
    	List<EmployeeDto> empList = new ArrayList<>();
		empList.add(emp);
		when(service.getEmployeeList()).thenReturn(empList);

		mockMvc.perform(get("/employees"))
//		.andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id));
    }
    
    @DisplayName("GET /employees/{id} should return employee")
    @Test
    public void getEmployee() throws Exception {
		when(service.getEmployeeById(id)).thenReturn(emp);

		mockMvc.perform(get("/employees/"+id))
//		.andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(name))
        .andExpect(jsonPath("$.id").value(id));
    }
    
    @DisplayName("GET /employees/{id} when id is invalid")
    @Test 
    public void getEmployee_invalid_id() throws Exception {
		when(service.getEmployeeById(id)).thenThrow(new ResourceNotFoundException(""));
		assertThrows(NestedServletException.class, () -> {
			mockMvc.perform(get("/employees/"+id))
	        .andExpect(status().is4xxClientError());
		});
    }
    
    @DisplayName("POST /employees should create a new employee")
    @Test
    public void createEmployee() throws Exception {
    	emp.setId(0);
		when(service.createEmployee(emp)).thenReturn(id);
    	
    	String body = "{" + 
    			"  \"name\": \""+name+"\",\n" + 
    			"  \"office\": null,\n" + 
    			"  \"email\" : null,\n" + 
    			"  \"phone\" : null,\n" + 
    			"  \"role\" : null\n" + 
    			"}";
		mockMvc.perform(post("/employees")
    	.content(body)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(id));
    }
    
    @DisplayName("PUT /employees/{id} should update employee")
    @Test
    public void updateEmployee() throws Exception {
    	String body = "{" + 
    			"  \"name\": \"new"+name+"\",\n" + 
    			"  \"office\": null,\n" + 
    			"  \"email\" : null,\n" + 
    			"  \"phone\" : null,\n" + 
    			"  \"role\" : null\n" + 
    			"}";
		mockMvc.perform(put("/employees/"+id)
		    	.content(body)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
        .andExpect(status().isOk());
    }

    @DisplayName("DELETE /employees/{id} should return employee")
    @Test
    public void deleteEmployee() throws Exception {
		mockMvc.perform(delete("/employees/"+id))
//		.andDo(print())
        .andExpect(status().isOk());
    }
}
