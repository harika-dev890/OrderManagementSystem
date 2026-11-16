package com.ty.OrderMangementSystem.dto;

import java.time.LocalDateTime;

import com.ty.OrderMangementSystem.entity.OrderStatus;

import lombok.Data;

@Data
public class OrderResponseDto {
	
	    private long orderId;
	    private LocalDateTime orderDate;
	    private double totalAmount;
	    private OrderStatus status;
	    private String customerName;
	

}
