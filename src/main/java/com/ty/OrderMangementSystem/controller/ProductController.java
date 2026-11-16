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

import com.ty.OrderMangementSystem.dto.ProductDto;
import com.ty.OrderMangementSystem.dto.ProductResponseDto;
import com.ty.OrderMangementSystem.service.ProductService;
import com.ty.OrderMangementSystem.util.ResponseStructure;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PostMapping
	public ResponseEntity<ResponseStructure<ProductResponseDto>> addNewPRoduct( @Valid @RequestBody ProductDto productDto){
		return productService.addProduct(productDto);
	}
	@GetMapping("/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponseDto>> findProductById(@PathVariable long productId){
		return productService.findProductById(productId);
			
	}
	@GetMapping
	public ResponseEntity<ResponseStructure<List<ProductResponseDto>>> findAllProducts(){
		return productService.findAllProducts();
			
	}
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PutMapping("/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponseDto>> UpdateProductById(@PathVariable long productId, @Valid @RequestBody ProductDto productDto){
		return productService.editProduct(productDto, productId);
			
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponseDto>> deleteProductById(@PathVariable long productId){
     	return productService.deleteProductById(productId);
			
	}
		

}
