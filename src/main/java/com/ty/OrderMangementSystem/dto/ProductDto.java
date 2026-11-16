package com.ty.OrderMangementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDto {
	@NotBlank
	private String name;
	@Positive
	private double price;
	@PositiveOrZero
	private int stock;
	@NotBlank
	private String category;

}
