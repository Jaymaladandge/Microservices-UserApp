package com.userService.exceptions;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException() {
		super("Reource not found on server !!");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
