package com.ty.OrderMangementSystem.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ty.OrderMangementSystem.util.ResponseStructure;
import com.ty.OrderMangementSystem.util.ResponseUtil;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleUserNotFoundException(UserNotFoundException ex){
		return  ResponseUtil.buildResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage(),
				null
				);
	}
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleOrderNotFoundException(OrderNotFoundException ex){
		return  ResponseUtil.buildResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage(),
				null
				);
	}
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleOrderNotFoundException(ProductNotFoundException ex){
		return  ResponseUtil.buildResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage(),
				null
				);
		
	}
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<ResponseStructure<String>> handleInsufficientStockException(InsufficientStockException ex){
		return  ResponseUtil.buildResponse(
				HttpStatus.BAD_REQUEST,
				ex.getMessage(),
				null
				);
		
	}
	@ExceptionHandler(OrderItemNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleOrderItemNotFoundException(OrderItemNotFoundException ex){
		return  ResponseUtil.buildResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage(),
				null
				);
		
	}
	@ExceptionHandler(UserExistException.class)
	public ResponseEntity<ResponseStructure<String>> handleUserExistException(UserExistException ex){
		return  ResponseUtil.buildResponse(
				HttpStatus.CONFLICT,
				ex.getMessage(),
				null
				);
		
	}
	

}
