package com.ty.OrderMangementSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {
	@NotBlank
	private String username;
	@Email
	@NotBlank
	private String email;
	@NotBlank
	@Size(min = 6 ,max = 12)
	private String password;

}
