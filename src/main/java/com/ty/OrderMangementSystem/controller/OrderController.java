package com.ty.OrderMangementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ty.OrderMangementSystem.dto.OrderDto;
import com.ty.OrderMangementSystem.dto.OrderResponseDto;
import com.ty.OrderMangementSystem.dto.OrderStatusDto;
import com.ty.OrderMangementSystem.service.OrderService;

import com.ty.OrderMangementSystem.util.ResponseStructure;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<OrderResponseDto>> addNewOrder( @Valid @RequestBody OrderDto orderDto){
		return orderService.addOrder(orderDto);
	}
	@GetMapping("/{orderId}")
	public ResponseEntity<ResponseStructure<OrderResponseDto>> findOrderById(@PathVariable long orderId){
		return orderService.findOrderById(orderId);
			
	}
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public ResponseEntity<ResponseStructure<List<OrderResponseDto>>> findAllOrders(){
		return orderService.findOrders();
			
	}
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PutMapping("/{orderId}")
	public ResponseEntity<ResponseStructure<OrderResponseDto>> UpdateOrderStatus(@PathVariable long orderId, @Valid @RequestBody OrderStatusDto orderStatusDto){
		return orderService.editOrderStatus(orderStatusDto, orderId);
			
	}
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ResponseStructure<OrderResponseDto>> deleteOrderById(@PathVariable long orderId){
     	return orderService.deleteOrderById(orderId);
			
	}

}
