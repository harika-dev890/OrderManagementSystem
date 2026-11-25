package com.ty.OrderMangementSystem.dto;

import com.ty.OrderMangementSystem.entity.Role;

import lombok.Data;

@Data
public class UserResponseDto {
	private long userId;
    private String username;
    private String email;
    private Role role;
    private String address;
    private String phnoNo;

}
