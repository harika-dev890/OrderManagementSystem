package com.ty.OrderMangementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ty.OrderMangementSystem.Exception.UserNotFoundException;
import com.ty.OrderMangementSystem.Repository.UserRepository;
import com.ty.OrderMangementSystem.dto.UserResponseDto;
import com.ty.OrderMangementSystem.entity.Role;
import com.ty.OrderMangementSystem.entity.User;
import com.ty.OrderMangementSystem.util.ResponseStructure;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public ResponseEntity<ResponseStructure<UserResponseDto>> findByUserId(long userId){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		User loggedInUser=userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("user name not found"));
		User targetUser=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user not found"));
		if(loggedInUser.getRole()==Role.MANAGER) {

			if( loggedInUser.getUserId()!=targetUser.getUserId() && targetUser.getRole()==Role.MANAGER ) {
				throw new AccessDeniedException("manager cannot fetch another manager");
			}
			if(targetUser.getRole()==Role.ADMIN) {
				throw new AccessDeniedException("manager cannot fetch admin details");
			}
			
		}
		if(loggedInUser.getRole()==Role.CUSTOMER) {
			if(loggedInUser.getUserId()!=targetUser.getUserId()) {
				throw new AccessDeniedException("customer cannot fetch another user details");
			}
		}
		UserResponseDto dto=new UserResponseDto();
		dto.setUserId(targetUser.getUserId());
		dto.setUsername(targetUser.getUsername());
		dto.setAddress(targetUser.getAddress());
		dto.setEmail(targetUser.getEmail());
		dto.setRole(targetUser.getRole());
		dto.setPhnoNo(targetUser.getPhnoNo());
		
		ResponseStructure<UserResponseDto> responseStructure=new ResponseStructure<UserResponseDto>();
		responseStructure.setMessage("user found");
		responseStructure.setData(dto);
		responseStructure.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<UserResponseDto>>(responseStructure,HttpStatus.OK);
		
		
	}
	public ResponseEntity<ResponseStructure<List<UserResponseDto>>> findAllUsers(){
		List<User> findAllUser =userRepository.findAll();
		ResponseStructure<List<UserResponseDto>> responseStructure=new ResponseStructure<List<UserResponseDto>>();
		List<UserResponseDto> allUsers=findAllUser.stream()
				           .map((items)->{
				        	   UserResponseDto dto=new UserResponseDto();
				        	   dto.setUserId(items.getUserId());
				        	   dto.setUsername(items.getUsername());
				        	   dto.setEmail(items.getEmail());
				        	   dto.setAddress(items.getAddress());
				        	   dto.setRole(items.getRole());
				        	   dto.setPhnoNo(items.getPhnoNo());
				        	   return dto;
				        	   
				           }).toList();
		
		responseStructure.setMessage("users found");
		responseStructure.setData(allUsers);
		responseStructure.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<List<UserResponseDto>>>(responseStructure,HttpStatus.OK);
		
		
	}
	
	public ResponseEntity<ResponseStructure<UserResponseDto>> updateUser(long userId,User user){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		User loggedInUser=userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("user name not found"));
		User targetUser=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user not found"));
		if(loggedInUser.getRole()==Role.MANAGER) {
			if( loggedInUser.getUserId()!=targetUser.getUserId() &&  targetUser.getRole()==Role.MANAGER) {
				throw new AccessDeniedException("manager cannot update another manager");
			}
			if(targetUser.getRole()==Role.ADMIN) {
				throw new AccessDeniedException("manager cannot update admin details");
			}
			
		}
		if(loggedInUser.getRole()==Role.CUSTOMER) {
			if(loggedInUser.getUserId()!=targetUser.getUserId()) {
				throw new AccessDeniedException("customer cannot update another user details");
			}
		}
		if (user.getPassword() != null && !user.getPassword().isBlank()) {
           targetUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
		if (user.getUsername() != null && !user.getUsername().isBlank()) {
		    targetUser.setUsername(user.getUsername());
		}

		if (user.getEmail() != null && !user.getEmail().isBlank()) {
		    targetUser.setEmail(user.getEmail());
		}

		if (user.getPhnoNo() != null && !user.getPhnoNo().isBlank()) {
		    targetUser.setPhnoNo(user.getPhnoNo());
		}

		if (user.getAddress() != null && !user.getAddress().isBlank()) {
		    targetUser.setAddress(user.getAddress());
		}

		if (user.getPassword() != null && !user.getPassword().isBlank()) {
		    targetUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}
	    User updatedUser=userRepository.save(targetUser);
	   UserResponseDto dto=new UserResponseDto();
	   dto.setUserId(updatedUser.getUserId());
	   dto.setUsername(updatedUser.getUsername());
	   dto.setAddress(updatedUser.getAddress());
	   dto.setEmail(updatedUser.getEmail());
	   dto.setRole(updatedUser.getRole());
	   dto.setPhnoNo(updatedUser.getPhnoNo());
	
	   ResponseStructure<UserResponseDto> responseStructure=new ResponseStructure<UserResponseDto>();
	   responseStructure.setMessage("user updated successfully");
	   responseStructure.setData(dto);
	   responseStructure.setStatusCode(HttpStatus.OK.value());
	
		return new ResponseEntity<ResponseStructure<UserResponseDto>>(responseStructure,HttpStatus.OK);
		
	}
	public ResponseEntity<ResponseStructure<String>> deleteUserById(long userId){
		User findUser=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user not found"));
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		userRepository.deleteById(findUser.getUserId());
		responseStructure.setMessage("user deleted");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
		
	}

}
