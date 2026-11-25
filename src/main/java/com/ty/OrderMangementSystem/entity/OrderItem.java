package com.ty.OrderMangementSystem.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long itemId;
	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal subTotal;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private Order order;
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private Product product;
	
	

}
