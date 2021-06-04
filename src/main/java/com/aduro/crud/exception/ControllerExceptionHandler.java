package com.aduro.crud.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice	
@SuppressWarnings({"unchecked", "rawtypes"})
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException e){
		List<String> details = new ArrayList<>();
		details.add(e.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Resource not found", details);
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> illegalArguments(IllegalArgumentException e){
		List<String> details = new ArrayList<>();
		details.add(e.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Invalid arguments", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> illegalArguments(EmptyResultDataAccessException e){
		List<String> details = new ArrayList<>();
		details.add(e.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Invalid arguments", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server Error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	 @Override
	 protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                                                  HttpHeaders headers,
	                                                                  HttpStatus status, WebRequest request) {
		List<String> details = ex.getBindingResult().getFieldErrors().stream()
								.map(error->error.getDefaultMessage())
								.collect(Collectors.toList());
		ErrorResponse error = new ErrorResponse("Invalid arguments", details);
		return new ResponseEntity(error, headers, status);
	 }
}
