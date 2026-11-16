package com.ty.OrderMangementSystem.dto;

import lombok.Data;

@Data
public class OrderItemResponseDto {
	  private long itemId;
	    private long productId;
	    private String productName;
	    private int quantity;
	    private double price;
	    private double totalPrice;

}
