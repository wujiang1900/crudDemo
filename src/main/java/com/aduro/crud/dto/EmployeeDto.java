package com.aduro.crud.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDto {
	
	private long id;
	
	@NotBlank
	@Size(max=100)
	private String name;
	
	@NotBlank
	@Pattern(regexp="[1-5][0-9][0-9](?i)[A-F]", 
			message="Please provide a valid office of range 100A – 599F")
	private String office;
	
	@NotBlank
	@Size(max=150)
	@Pattern(regexp="^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$", 
			message="Please provide a valid email address")
	private String email;
	
	@NotBlank
	@Pattern(regexp="^(\\d{3}[-/ .]?){2}\\d{4}$", 
			message="Please provide a valid phone number")
	private String phone;
	
	@NotBlank
	@Size(max=150)
	private String role;
}
