package com.ty.OrderMangementSystem.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductResponseDto {
	 private long productId;
	    private String name;
	    private BigDecimal price;
	    private int stock;
	    private String category;

}
