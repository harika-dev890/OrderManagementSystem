package com.ty.OrderMangementSystem.dto;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
public class OrderDto {
	 @Positive
	    private long userId;

}
