package com.ty.OrderMangementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ty.OrderMangementSystem.dto.RegisterRequestDto;
import com.ty.OrderMangementSystem.entity.User;
import com.ty.OrderMangementSystem.service.AuthenticationService;
import com.ty.OrderMangementSystem.util.ResponseStructure;
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AuthenticationService authenticationService;
	 
	@PreAuthorize("hasRole('ADMIN')")
	 @PostMapping("/registerManager")
		public ResponseEntity<ResponseStructure<User>> registerManager(@RequestBody RegisterRequestDto registerRequestDto){
			return authenticationService.registerManager(registerRequestDto);
			
		}
	@PreAuthorize("hasRole('ADMIN')")
	 @PostMapping("/registerAdmin")
		public ResponseEntity<ResponseStructure<User>> registerAdmin(@RequestBody RegisterRequestDto registerRequestDto){
			return authenticationService.registerAdmin(registerRequestDto);
			
		}

}
