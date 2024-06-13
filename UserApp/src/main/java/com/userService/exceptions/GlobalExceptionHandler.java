package com.userService.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.userService.payload.APIResponse;

//@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<APIResponse> globalExceptionObj(Exception ex){
		logger.info("===============globalExceptionObj==================");
		APIResponse response = APIResponse.builder().message(ex.getMessage()).success(false).build();
		return ResponseEntity.internalServerError().body(response);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		logger.info("===============resourceNotFoundExceptionHandler==================");
		APIResponse apiResponse = APIResponse.builder().message(ex.getMessage()+"===="+ex.getCause()).success(true).status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	
}
