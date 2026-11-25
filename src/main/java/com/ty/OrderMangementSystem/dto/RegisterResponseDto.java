package com.ty.OrderMangementSystem.dto;

import com.ty.OrderMangementSystem.entity.Role;

import lombok.Data;

@Data
public class RegisterResponseDto {
	
	private String username;
	private String email;
	private Role role;

}
