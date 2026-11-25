package com.ty.OrderMangementSystem.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemResponseDto {
	  private long itemId;
	    private long productId;
	    private String productName;
	    private int quantity;
	    private BigDecimal price;
	    private BigDecimal totalPrice;

}
