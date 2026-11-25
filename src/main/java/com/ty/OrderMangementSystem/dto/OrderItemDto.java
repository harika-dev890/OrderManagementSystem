package com.ty.OrderMangementSystem.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemDto {

	 @Positive
	private long orderId;
	 @Positive
	private long productId;
	@Positive
	private int quantity;

}
