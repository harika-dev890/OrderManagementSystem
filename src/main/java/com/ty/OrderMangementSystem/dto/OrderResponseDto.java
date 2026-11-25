package com.ty.OrderMangementSystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ty.OrderMangementSystem.entity.OrderStatus;

import lombok.Data;

@Data
public class OrderResponseDto {
	
	    private long orderId;
	    private LocalDateTime orderDate;
	    private BigDecimal totalAmount;
	    private OrderStatus status;
	    private String customerName;
	

}
