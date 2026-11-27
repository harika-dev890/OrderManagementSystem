package com.ty.OrderMangementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ty.OrderMangementSystem.dto.LoginRequestDto;
import com.ty.OrderMangementSystem.dto.LoginResponseDto;
import com.ty.OrderMangementSystem.dto.RegisterRequestDto;
import com.ty.OrderMangementSystem.dto.RegisterResponseDto;
import com.ty.OrderMangementSystem.service.AuthenticationService;
import com.ty.OrderMangementSystem.util.ResponseStructure;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationService authenticationService;
	 @PostMapping("/registerCustomer")
		public ResponseEntity<ResponseStructure<RegisterResponseDto>> registerUser(@valid @RequestBody RegisterRequestDto registerRequestDto){
			return authenticationService.registerCustomer(registerRequestDto);
			
		}
	

	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<LoginResponseDto>> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto){
		return authenticationService.loginUser(loginRequestDto);
	}

}
