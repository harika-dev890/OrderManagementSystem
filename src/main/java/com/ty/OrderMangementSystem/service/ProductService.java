package com.ty.OrderMangementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.OrderMangementSystem.Exception.ProductNotFoundException;
import com.ty.OrderMangementSystem.Repository.ProductRepository;
import com.ty.OrderMangementSystem.dto.ProductDto;
import com.ty.OrderMangementSystem.dto.ProductResponseDto;
import com.ty.OrderMangementSystem.entity.Product;
import com.ty.OrderMangementSystem.util.ResponseStructure;
import com.ty.OrderMangementSystem.util.ResponseUtil;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	public ResponseEntity<ResponseStructure<ProductResponseDto>> addProduct(ProductDto productRequest) {
		
		Product setProduct=new Product();
		
		setProduct.setName(productRequest.getName());
		setProduct.setPrice(productRequest.getPrice());
		setProduct.setCategory(productRequest.getCategory());
		setProduct.setStock(productRequest.getStock());
		
		Product newProduct=productRepository.save(setProduct);
		ProductResponseDto productResponseDto=new ProductResponseDto();
		productResponseDto.setName(newProduct.getName());
		productResponseDto.setPrice(newProduct.getPrice());
		productResponseDto.setProductId(newProduct.getProductId());
		productResponseDto.setStock(newProduct.getStock());
		return ResponseUtil.buildResponse(
		        HttpStatus.CREATED,
		        "Product created successfully",
		        productResponseDto);
		
	}
	
	public ResponseEntity<ResponseStructure<ProductResponseDto>> findProductById(long productId) {
		Product findProduct=productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("product not found"));
		ProductResponseDto productResponseDto=new ProductResponseDto();
		productResponseDto.setName(findProduct.getName());
		productResponseDto.setPrice(findProduct.getPrice());
		productResponseDto.setProductId(findProduct.getProductId());
		productResponseDto.setStock(findProduct.getStock());
		
		return ResponseUtil.buildResponse(
				HttpStatus.OK,
				"product found Successfully",
				productResponseDto );
	}
	public ResponseEntity<ResponseStructure<List<ProductResponseDto>>> findAllProducts(){
		List<Product> allProducts=productRepository.findAll();
		if (allProducts.isEmpty()) {
		      throw new ProductNotFoundException("No products present");
		}
		List<ProductResponseDto> productResponseDtos=allProducts.stream()
				                                                .map((items)->{
				                                                	ProductResponseDto productResponseDto=new ProductResponseDto();
				                                            		productResponseDto.setName(items.getName());
				                                            		productResponseDto.setPrice(items.getPrice());
				                                            		productResponseDto.setProductId(items.getProductId());
				                                            		productResponseDto.setStock(items.getStock());
				                                            		return productResponseDto;
				                                                	
				                                                }).toList();
		return ResponseUtil.buildResponse(
				HttpStatus.OK,
				"product found Successfully",
				productResponseDtos );
	}
	public ResponseEntity<ResponseStructure<ProductResponseDto>> editProduct(ProductDto productRequest,long productId){
		Product findProduct=productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("product not found"));
		
		findProduct.setName(productRequest.getName());
		findProduct.setPrice(productRequest.getPrice());
		findProduct.setCategory(productRequest.getCategory());
		findProduct.setStock(productRequest.getStock());
		
		Product updatedProduct=productRepository.save(findProduct);
		ProductResponseDto productResponseDto=new ProductResponseDto();
		productResponseDto.setName(updatedProduct.getName());
		productResponseDto.setPrice(updatedProduct.getPrice());
		productResponseDto.setProductId(updatedProduct.getProductId());
		productResponseDto.setStock(updatedProduct.getStock());
		return ResponseUtil.buildResponse(
		        HttpStatus.OK,
		        "Product updated successfully",
		        productResponseDto);
		
	}
	public ResponseEntity<ResponseStructure<ProductResponseDto>> deleteProductById(long productId) {
		Product findProduct=productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("product not found"));
		productRepository.deleteById(findProduct.getProductId());
		ProductResponseDto productResponseDto=new ProductResponseDto();
		productResponseDto.setName(findProduct.getName());
		productResponseDto.setPrice(findProduct.getPrice());
		productResponseDto.setProductId(findProduct.getProductId());
		productResponseDto.setStock(findProduct.getStock());
		
		return ResponseUtil.buildResponse(
				HttpStatus.OK,
				"product deleted Successfully",
				productResponseDto );
	}
	
}
	