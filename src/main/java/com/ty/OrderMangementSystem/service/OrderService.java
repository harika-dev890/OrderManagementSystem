package com.ty.OrderMangementSystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ty.OrderMangementSystem.Exception.OrderNotFoundException;
import com.ty.OrderMangementSystem.Exception.UserNotFoundException;
import com.ty.OrderMangementSystem.Repository.OrderRepository;
import com.ty.OrderMangementSystem.Repository.UserRepository;
import com.ty.OrderMangementSystem.dto.OrderDto;
import com.ty.OrderMangementSystem.dto.OrderResponseDto;
import com.ty.OrderMangementSystem.dto.OrderStatusDto;
import com.ty.OrderMangementSystem.entity.Order;
import com.ty.OrderMangementSystem.entity.OrderStatus;
import com.ty.OrderMangementSystem.entity.Role;
import com.ty.OrderMangementSystem.entity.User;
import com.ty.OrderMangementSystem.util.ResponseStructure;
import com.ty.OrderMangementSystem.util.ResponseUtil;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	
	public ResponseEntity<ResponseStructure<OrderResponseDto>> addOrder(OrderDto orderDto){
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
        User loggedInUser = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("user not found"));
        if(loggedInUser.getRole()== Role.CUSTOMER) {
        	if (!loggedInUser.getUserId().equals(orderDto.getUserId())) {
                throw new AccessDeniedException("You can create orders only for yourself.");
        	}
        	
        }
        User orderUser = userRepository.findById(orderDto.getUserId()).orElseThrow(()->new UserNotFoundException("Customer not found."));
        if (orderUser.getRole() != Role.CUSTOMER) {
            throw new AccessDeniedException("Orders can only be created for customers.");
        }
	
	    Order order = new Order();

	    order.setUser(orderUser);
	    order.setOrderDate(LocalDateTime.now());
	    order.setStatus(OrderStatus.PENDING);
	    order.setTotalAmount(0);
	    Order savedOrder = orderRepository.save(order);
	    OrderResponseDto orderResponseDto=new OrderResponseDto();
	    orderResponseDto.setOrderId(savedOrder.getOrderId());
	    orderResponseDto.setOrderDate(savedOrder.getOrderDate());
	    orderResponseDto.setStatus(savedOrder.getStatus());
	    orderResponseDto.setCustomerName(savedOrder.getUser().getUsername());
	    orderResponseDto.setTotalAmount(savedOrder.getTotalAmount());

	    

	    return ResponseUtil.buildResponse(
	            HttpStatus.CREATED,
	            "Order created successfully",
	            orderResponseDto
	    );
		
		
	}
	public ResponseEntity<ResponseStructure<OrderResponseDto>> findOrderById(long orderId){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		User loggedInUser=userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("user not found"));
		Order findOrder=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("order not found"));
		if(loggedInUser.getRole()==Role.CUSTOMER) {
			if(!findOrder.getUser().getUserId().equals(loggedInUser.getUserId())) {
				throw new AccessDeniedException("customer cannot fetch another users order");
			}
		}
		OrderResponseDto orderResponseDto=new OrderResponseDto();
		orderResponseDto.setCustomerName(findOrder.getUser().getUsername());
		orderResponseDto.setOrderDate(findOrder.getOrderDate());
		orderResponseDto.setOrderId(findOrder.getOrderId());
		orderResponseDto.setStatus(findOrder.getStatus());
		orderResponseDto.setTotalAmount(findOrder.getTotalAmount());
		
		return ResponseUtil.buildResponse(HttpStatus.OK, "order found", orderResponseDto);
		
		
	}
	
	public ResponseEntity<ResponseStructure<List<OrderResponseDto>>> findOrders(){
		List<Order> findOrders=orderRepository.findAll();
		if(findOrders.isEmpty()) {
			throw new OrderNotFoundException("no orders present");
		}
		List<OrderResponseDto> orderResponseDtos=findOrders.stream()
				                                     .map(item->{
				                                    	 OrderResponseDto dto=new OrderResponseDto();
				                                    	 dto.setCustomerName(item.getUser().getUsername());
				                                    	 dto.setOrderDate(item.getOrderDate());
				                                    	 dto.setOrderId(item.getOrderId());
				                                    	 dto.setStatus(item.getStatus());
				                                    	 dto.setTotalAmount(item.getTotalAmount());
				                                    	 return dto;
				                                    	 
				                                     }
				                                    
				                                     ).toList();
		
		return ResponseUtil.buildResponse(HttpStatus.OK, "order found", orderResponseDtos);
		
		
	}
	public ResponseEntity<ResponseStructure<OrderResponseDto>> editOrderStatus(OrderStatusDto orderStatus,long orderId){
		Order findOrder=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("order not found"));

		  
		   findOrder.setStatus(orderStatus.getStatus());
		
	
		    Order savedOrder = orderRepository.save(findOrder);
		    
		    OrderResponseDto orderResponseDto=new OrderResponseDto();
		    orderResponseDto.setStatus(savedOrder.getStatus());

		    return ResponseUtil.buildResponse(
		            HttpStatus.OK,
		            "Order updated successfully",
		            orderResponseDto
		    );
			
			
		}
	public ResponseEntity<ResponseStructure<OrderResponseDto>> deleteOrderById(long orderId){
		Order findOrder=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("order not found"));
		
		orderRepository.deleteById(findOrder.getOrderId());
		OrderResponseDto orderResponseDto=new OrderResponseDto();
		orderResponseDto.setCustomerName(findOrder.getUser().getUsername());
		orderResponseDto.setOrderDate(findOrder.getOrderDate());
		orderResponseDto.setOrderId(findOrder.getOrderId());
		orderResponseDto.setStatus(findOrder.getStatus());
		orderResponseDto.setTotalAmount(findOrder.getTotalAmount());
		
		return ResponseUtil.buildResponse(HttpStatus.OK, "order deleted", orderResponseDto);
		
		
	}
	
	

}
