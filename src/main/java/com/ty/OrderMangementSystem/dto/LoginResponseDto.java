package com.ty.OrderMangementSystem.dto;



import com.ty.OrderMangementSystem.entity.Role;

import lombok.Data;
@Data
public class LoginResponseDto {
	private String userName;
	private String message;
	private Role role;
	private String token;

}
