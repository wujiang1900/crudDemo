package com.aduro.crud.exception;

public class ResourceNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5728071317071582871L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
