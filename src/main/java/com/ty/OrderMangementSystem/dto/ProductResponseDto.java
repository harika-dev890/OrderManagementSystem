package com.ty.OrderMangementSystem.dto;

import lombok.Data;

@Data
public class ProductResponseDto {
	 private long productId;
	    private String name;
	    private double price;
	    private int stock;


}
