package com.techleads.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyControllerAdvice {
	
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<String> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex ){
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
}
