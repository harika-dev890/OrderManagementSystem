package com.ty.OrderMangementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ty.OrderMangementSystem.dto.UserResponseDto;
import com.ty.OrderMangementSystem.entity.User;
import com.ty.OrderMangementSystem.service.UserService;
import com.ty.OrderMangementSystem.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@GetMapping("/{userId}")
	public ResponseEntity<ResponseStructure<UserResponseDto>> findUserById(@PathVariable long userId){
		return userService.findByUserId(userId);
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<ResponseStructure<List<UserResponseDto>>> findAllUsers(){
		return userService.findAllUsers();
		
	}
	@PatchMapping("/{userId}")
	public ResponseEntity<ResponseStructure<UserResponseDto>> UpdateUserById(@PathVariable long userId, @Valid @RequestBody User user){
		return userService.updateUser(userId, user);
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ResponseStructure<String>> deleteUserById(@PathVariable long userId){
		return userService.deleteUserById(userId);
		
	}
	
	
	

}
