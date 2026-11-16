package com.ty.OrderMangementSystem.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
	
	public static<T>  ResponseEntity<ResponseStructure<T>> buildResponse(HttpStatus status ,String message,T data){
		ResponseStructure<T> responseStructure=new ResponseStructure<T>();
		responseStructure.setData(data);
		responseStructure.setMessage(message);
		responseStructure.setStatusCode(status.value());
		return new ResponseEntity<ResponseStructure<T>>(responseStructure,status);
		
	}

}
