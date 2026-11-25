package com.ty.OrderMangementSystem.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ty.OrderMangementSystem.Exception.InsufficientStockException;
import com.ty.OrderMangementSystem.Exception.OrderItemNotFoundException;
import com.ty.OrderMangementSystem.Exception.OrderNotFoundException;
import com.ty.OrderMangementSystem.Exception.ProductNotFoundException;
import com.ty.OrderMangementSystem.Exception.UserNotFoundException;
import com.ty.OrderMangementSystem.Repository.OrderItemRepository;
import com.ty.OrderMangementSystem.Repository.OrderRepository;
import com.ty.OrderMangementSystem.Repository.ProductRepository;
import com.ty.OrderMangementSystem.Repository.UserRepository;
import com.ty.OrderMangementSystem.dto.OrderItemDto;
import com.ty.OrderMangementSystem.dto.OrderItemResponseDto;
import com.ty.OrderMangementSystem.entity.Order;
import com.ty.OrderMangementSystem.entity.OrderItem;
import com.ty.OrderMangementSystem.entity.OrderStatus;
import com.ty.OrderMangementSystem.entity.Product;
import com.ty.OrderMangementSystem.entity.Role;
import com.ty.OrderMangementSystem.entity.User;
import com.ty.OrderMangementSystem.util.ResponseStructure;
import com.ty.OrderMangementSystem.util.ResponseUtil;



@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	
     @Transactional
	public ResponseEntity<ResponseStructure<OrderItemResponseDto>> createOrderItem(OrderItemDto itemDto){
    	 Authentication authentication =SecurityContextHolder.getContext().getAuthentication();

    		String username = authentication.getName();

    		User loggedInUser = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("user not found"));

    		Product findProduct=productRepository.findById(itemDto.getProductId()).orElseThrow(()-> new ProductNotFoundException("product not found"));
    		Order findOrder=orderRepository.findById(itemDto.getOrderId()).orElseThrow(()->new OrderNotFoundException("order not found"));
    		if(loggedInUser.getRole()==Role.CUSTOMER) {

    		    if(findOrder.getUser().getUserId()!=loggedInUser.getUserId()) {
    		        throw new AccessDeniedException("cannot add item to another user's order");
    		    }
    		}
    		if (findOrder.getStatus() == OrderStatus.DELIVERED || findOrder.getStatus() == OrderStatus.CANCELLED) {

    		    throw new IllegalArgumentException( "Cannot add items to a delivered or cancelled order");
    		}
		
		OrderItem orderItem=new OrderItem();
		if(findProduct.getStock()>=itemDto.getQuantity()) {
			orderItem.setQuantity(itemDto.getQuantity());
			findProduct.setStock(findProduct.getStock()-orderItem.getQuantity());
		}else {
			throw new InsufficientStockException("insufficient stock");
		}
		orderItem.setPrice(findProduct.getPrice());
		orderItem.setSubTotal(findProduct.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
		findOrder.setTotalAmount(findOrder.getTotalAmount().add(orderItem.getSubTotal()));
		orderItem.setProduct(findProduct);
		orderItem.setOrder(findOrder);
		
		productRepository.save(findProduct);
		orderRepository.save(findOrder);
		
		orderItemRepository.save(orderItem);
		
		OrderItemResponseDto orderItemResponseDto=new OrderItemResponseDto();
		orderItemResponseDto.setItemId(orderItem.getItemId());
		orderItemResponseDto.setPrice(orderItem.getPrice());
		orderItemResponseDto.setProductId(orderItem.getProduct().getProductId());
		orderItemResponseDto.setProductName(orderItem.getProduct().getName());
		orderItemResponseDto.setQuantity(orderItem.getQuantity());
		orderItemResponseDto.setTotalPrice(orderItem.getSubTotal());
		
		return ResponseUtil.buildResponse(HttpStatus.CREATED, "order item created", orderItemResponseDto);	
	}
     
     public ResponseEntity<ResponseStructure<OrderItemResponseDto>> findOrderItemById(long orderItemId){
    	 Authentication authentication =SecurityContextHolder.getContext().getAuthentication();

 		String username = authentication.getName();
 		
 		User loggedInUser = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("user not found"));
 		 OrderItem existingOrderItem=orderItemRepository.findById(orderItemId).orElseThrow(()-> new OrderItemNotFoundException("item not found in order"));
 		
 		if(loggedInUser.getRole()==Role.CUSTOMER) {

 		    if(loggedInUser.getUserId()!=existingOrderItem.getOrder().getUser().getUserId()) {
 		        throw new AccessDeniedException("You are not authorized to view this order item");
 		    }
 		    
 		}
 		OrderItemResponseDto orderItemResponseDto=new OrderItemResponseDto();
 		orderItemResponseDto.setItemId(existingOrderItem.getItemId());
 		orderItemResponseDto.setPrice(existingOrderItem.getPrice());
    	orderItemResponseDto.setProductId(existingOrderItem.getProduct().getProductId());
    	orderItemResponseDto.setProductName(existingOrderItem.getProduct().getName());
    	orderItemResponseDto.setQuantity(existingOrderItem.getQuantity());
    	orderItemResponseDto.setTotalPrice(existingOrderItem.getSubTotal());
 		

    	 return ResponseUtil.buildResponse(HttpStatus.OK, "item found in order", orderItemResponseDto);
     }
     public ResponseEntity<ResponseStructure<List<OrderItemResponseDto>>> findAllOrderItems(){
    	 List<OrderItem> existingOrderItem=orderItemRepository.findAll();
    	 List<OrderItemResponseDto> orderItemResponseDtos=existingOrderItem.stream()
    			                                                            .map((items)->
    			                                                            {
    			                                                            	OrderItemResponseDto orderItemResponseDto=new OrderItemResponseDto();
    			                                                            	orderItemResponseDto.setItemId(items.getItemId());
    			                                                            	orderItemResponseDto.setPrice(items.getPrice());
    			                                                            	orderItemResponseDto.setProductId(items.getProduct().getProductId());
    			                                                            	orderItemResponseDto.setProductName(items.getProduct().getName());
    			                                                            	orderItemResponseDto.setQuantity(items.getQuantity());
    			                                                            	orderItemResponseDto.setTotalPrice(items.getSubTotal());
    			                                                            	return orderItemResponseDto;
    			                                                            }).toList();
    	 
    	 return ResponseUtil.buildResponse(HttpStatus.OK, "Order items retrieved successfully", orderItemResponseDtos);
     }
     @Transactional
     public ResponseEntity<ResponseStructure<OrderItemResponseDto>> updateOrderItem(long orderItemId,OrderItemDto itemDto){
    	 OrderItem existingOrderItem=orderItemRepository.findById(orderItemId).orElseThrow(()-> new OrderItemNotFoundException("item not found in order"));
    	 Product findProduct=productRepository.findById(existingOrderItem.getProduct().getProductId()).orElseThrow(()-> new ProductNotFoundException("product not found"));
 		Order findOrder=orderRepository.findById(existingOrderItem.getOrder().getOrderId()).orElseThrow(()->new OrderNotFoundException("order not found"));
 		Authentication authentication =
 		        SecurityContextHolder.getContext().getAuthentication();

 		User loggedInUser =
 		        userRepository.findByUsername(authentication.getName())
 		        .orElseThrow(() -> new UserNotFoundException("user not found"));


 		if(loggedInUser.getRole()==Role.CUSTOMER) {

 		    if(findOrder.getUser().getUserId()!=loggedInUser.getUserId()) {
 		        throw new AccessDeniedException(
 		        "cannot update another user's order item");
 		    }
 		}
 		if (findOrder.getStatus() == OrderStatus.DELIVERED || findOrder.getStatus() == OrderStatus.CANCELLED) {
		    throw new IllegalArgumentException( "Cannot update items in a delivered or cancelled order");
		}
 		findProduct.setStock(findProduct.getStock()+existingOrderItem.getQuantity());
 		
 		if(findProduct.getStock()<itemDto.getQuantity()) {
    		 throw new InsufficientStockException("insufficient stock");
    	 } 
    	findProduct.setStock(findProduct.getStock()-itemDto.getQuantity());
    	findOrder.setTotalAmount(findOrder.getTotalAmount().subtract(existingOrderItem.getSubTotal()));
    	existingOrderItem.setQuantity(itemDto.getQuantity());
    	existingOrderItem.setSubTotal(existingOrderItem.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
    	findOrder.setTotalAmount(findOrder.getTotalAmount().add(existingOrderItem.getSubTotal()));
    	existingOrderItem.setProduct(findProduct);
    	existingOrderItem.setOrder(findOrder);
    	productRepository.save(findProduct);
    	orderRepository.save(findOrder);
    			
    	OrderItem updatedOrderItem=orderItemRepository.save(existingOrderItem);
    	OrderItemResponseDto orderItemResponseDto=new OrderItemResponseDto();
    	orderItemResponseDto.setPrice(updatedOrderItem.getPrice());
    	orderItemResponseDto.setItemId(updatedOrderItem.getItemId());
    	orderItemResponseDto.setProductId(updatedOrderItem.getProduct().getProductId());
    	orderItemResponseDto.setProductName(updatedOrderItem.getProduct().getName());
    	orderItemResponseDto.setQuantity(updatedOrderItem.getQuantity());
    	orderItemResponseDto.setTotalPrice(updatedOrderItem.getSubTotal());
    	return ResponseUtil.buildResponse(HttpStatus.OK, "order item updated", orderItemResponseDto);
    	
     }
     @Transactional
     public ResponseEntity<ResponseStructure<OrderItemResponseDto>> deleteOrderItemById(long orderItemId){
    	 OrderItem existingOrderItem=orderItemRepository.findById(orderItemId).orElseThrow(()-> new OrderItemNotFoundException("item not found in order"));
    	 Product product=productRepository.findById(existingOrderItem.getProduct().getProductId()).orElseThrow(()->new ProductNotFoundException("product not found"));
    	 Order order=orderRepository.findById(existingOrderItem.getOrder().getOrderId()).orElseThrow(()-> new OrderNotFoundException("order not found"));
    	 Authentication authentication =
    		        SecurityContextHolder.getContext().getAuthentication();

    		User loggedInUser =
    		        userRepository.findByUsername(authentication.getName())
    		        .orElseThrow(() -> new UserNotFoundException("user not found"));


    		if(loggedInUser.getRole()==Role.CUSTOMER) {

    		    if(existingOrderItem.getOrder().getUser().getUserId()!= loggedInUser.getUserId()) {

    		        throw new AccessDeniedException("cannot delete another user's order item");
    		    }
    		}
    		if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
    		    throw new IllegalArgumentException( "Cannot delete items from a delivered or cancelled order");
    		}
    	 product.setStock(product.getStock()+existingOrderItem.getQuantity());
    	 order.setTotalAmount(order.getTotalAmount().subtract(existingOrderItem.getSubTotal()));
    	 productRepository.save(product);
    	 orderRepository.save(order);
    	 orderItemRepository.deleteById(existingOrderItem.getItemId());
    	 
    	 OrderItemResponseDto orderItemResponseDto=new OrderItemResponseDto();
    	 orderItemResponseDto.setItemId(existingOrderItem.getItemId());
    	 orderItemResponseDto.setPrice(existingOrderItem.getPrice());
    	 orderItemResponseDto.setProductId(existingOrderItem.getProduct().getProductId());
    	 orderItemResponseDto.setProductName(existingOrderItem.getProduct().getName());
    	 orderItemResponseDto.setQuantity(existingOrderItem.getQuantity());
    	 orderItemResponseDto.setTotalPrice(existingOrderItem.getSubTotal());
    	 
    	 
    	 return ResponseUtil.buildResponse(HttpStatus.OK, "Order item deleted successfully", orderItemResponseDto);
    	 
     }

     
     

}
