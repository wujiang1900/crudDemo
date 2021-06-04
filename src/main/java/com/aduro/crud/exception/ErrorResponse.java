package com.aduro.crud.exception;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "error")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse 
{
    //General error message about nature of error
    private String message;
 
    //Specific errors during API request processing
    private List<String> details;
}