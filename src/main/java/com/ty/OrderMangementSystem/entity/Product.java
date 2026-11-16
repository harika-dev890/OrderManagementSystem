package com.ty.OrderMangementSystem.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	private String name;
	private double price;
	private int stock;
	private String category;
	
	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItems;
	
	
	

}
