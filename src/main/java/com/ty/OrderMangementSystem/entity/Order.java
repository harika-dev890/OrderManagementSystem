package com.ty.OrderMangementSystem.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long orderId;
	@Column(nullable = false)
	private LocalDateTime orderDate;
	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal totalAmount;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private User user;
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;
	@Version
	private Long version;

}
