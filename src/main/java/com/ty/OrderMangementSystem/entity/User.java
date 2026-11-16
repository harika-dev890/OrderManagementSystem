package com.ty.OrderMangementSystem.entity;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	private String username;
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String address;
	private String phnoNo;
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	

}
