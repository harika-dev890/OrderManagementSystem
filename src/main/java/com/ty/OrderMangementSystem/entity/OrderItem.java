package com.ty.OrderMangementSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long itemId;
	private int quantity;
	private double price;
	private double subTotal;
	
	@ManyToOne
	private Order order;
	@ManyToOne
	private Product product;
	
	

}
