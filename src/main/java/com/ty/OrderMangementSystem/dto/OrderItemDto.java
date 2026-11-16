package com.ty.OrderMangementSystem.dto;

import lombok.Data;

@Data
public class OrderItemDto {
	private long orderId;
	private long productId;
	private int quantity;

}
