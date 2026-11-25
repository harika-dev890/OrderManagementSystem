package com.ty.OrderMangementSystem.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	@Column(nullable = false)
	private String name;
	@Column(precision = 10, scale = 2,nullable = false)
	private BigDecimal price;
	@Column(nullable=false)
	private int stock;
	@Column(nullable = false)
	private String category;
	
	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItems;
	@Version
	private Long version;
	
	
	

}
