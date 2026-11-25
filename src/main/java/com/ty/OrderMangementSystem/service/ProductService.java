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
		productResponseDto.setCategory(newProduct.getCategory());
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
		productResponseDto.setCategory(findProduct.getCategory());
		
		return ResponseUtil.buildResponse(
				HttpStatus.OK,
				"product found Successfully",
				productResponseDto );
	}
	public ResponseEntity<ResponseStructure<List<ProductResponseDto>>> findAllProducts(){
		List<Product> allProducts=productRepository.findAll();
		List<ProductResponseDto> productResponseDtos=allProducts.stream()
				                                                .map((items)->{
				                                                	ProductResponseDto productResponseDto=new ProductResponseDto();
				                                            		productResponseDto.setName(items.getName());
				                                            		productResponseDto.setPrice(items.getPrice());
				                                            		productResponseDto.setProductId(items.getProductId());
				                                            		productResponseDto.setStock(items.getStock());
				                                            		productResponseDto.setCategory(items.getCategory());
				                                            		return productResponseDto;
				                                                	
				                                                }).toList();
		return ResponseUtil.buildResponse(
				HttpStatus.OK,
				"product found Successfully",
				productResponseDtos );
	}

    public ResponseEntity<ResponseStructure<ProductResponseDto>> editProduct(ProductDto productRequest, long productId) {
    	Product findProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("product not found"));
        if (productRequest.getName() != null) {
        	if (productRequest.getName().isBlank()) {
        		throw new IllegalArgumentException("Product name cannot be blank");
            }
             findProduct.setName(productRequest.getName());
        }
        if (productRequest.getPrice() != null) {
             findProduct.setPrice(productRequest.getPrice());
        }

        if (productRequest.getStock() != null) {
           findProduct.setStock(productRequest.getStock());
        }

       if (productRequest.getCategory() != null) {
           if (productRequest.getCategory().isBlank()) {
               throw new IllegalArgumentException("Category cannot be blank");
           }
           findProduct.setCategory(productRequest.getCategory());
       }
       Product updatedProduct = productRepository.save(findProduct);
       ProductResponseDto dto = new ProductResponseDto();
        dto.setName(updatedProduct.getName());
        dto.setPrice(updatedProduct.getPrice());
        dto.setProductId(updatedProduct.getProductId());
        dto.setStock(updatedProduct.getStock());
        dto.setCategory(updatedProduct.getCategory());
         return ResponseUtil.buildResponse(
            HttpStatus.OK,
            "Product updated successfully",
            dto);
      }

	public ResponseEntity<ResponseStructure<String>> deleteProductById(long productId) {
		Product findProduct=productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("product not found"));
		productRepository.deleteById(findProduct.getProductId());
		
		
		return ResponseUtil.buildResponse(
				HttpStatus.OK,
				"product deleted Successfully",
				 "Product deleted successfully"
				 );
	}
	
}
	