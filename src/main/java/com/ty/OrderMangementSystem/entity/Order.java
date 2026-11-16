package com.ty.OrderMangementSystem.entity;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long orderId;
	private LocalDateTime orderDate;
	private double totalAmount;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;

}
