package com.ty.OrderMangementSystem.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDto {
	@NotBlank
	private String name;
	@Positive
	private BigDecimal price;
	@PositiveOrZero
	private Integer stock;
	@NotBlank
	private String category;

}
