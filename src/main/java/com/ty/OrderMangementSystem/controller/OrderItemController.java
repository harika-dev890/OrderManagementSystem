package com.ty.OrderMangementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ty.OrderMangementSystem.dto.OrderItemDto;
import com.ty.OrderMangementSystem.dto.OrderItemResponseDto;
import com.ty.OrderMangementSystem.service.OrderItemService;

import com.ty.OrderMangementSystem.util.ResponseStructure;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/orderItems")
public class OrderItemController {
	@Autowired
	private OrderItemService orderItemService;
	@PostMapping
	public ResponseEntity<ResponseStructure<OrderItemResponseDto>> addNewOrderItem( @Valid @RequestBody OrderItemDto orderItemDto){
		return orderItemService.createOrderItem(orderItemDto);
	}
	@GetMapping("/{orderItemId}")
	public ResponseEntity<ResponseStructure<OrderItemResponseDto>> findOrderItemById(@PathVariable long orderItemId){
		return orderItemService.findOrderItemById(orderItemId);
			
	}
	 @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public ResponseEntity<ResponseStructure<List<OrderItemResponseDto>>> findAllOrderItems(){
		return orderItemService.findAllOrderItems();
			
	}
	@PatchMapping("/{orderItemId}")
	public ResponseEntity<ResponseStructure<OrderItemResponseDto>> updateOrderItem(@PathVariable long orderItemId, @Valid @RequestBody OrderItemDto orderItemDto){
		return orderItemService.updateOrderItem(orderItemId, orderItemDto);
			
	}
	@DeleteMapping("/{orderItemId}")
	public ResponseEntity<ResponseStructure<OrderItemResponseDto>> deleteOrderItemById(@PathVariable long orderItemId){
     	return orderItemService.deleteOrderItemById(orderItemId);
			
	}

}
