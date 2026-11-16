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
import com.ty.OrderMangementSystem.entity.Role;
import com.ty.OrderMangementSystem.entity.User;
import com.ty.OrderMangementSystem.util.ResponseStructure;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public ResponseEntity<ResponseStructure<User>> findByUserId(long userId){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		User loggedInUser=userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("user name not found"));
		User targetUser=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("usernot found"));
		if(loggedInUser.getRole()==Role.MANAGER) {
			if(targetUser.getRole()==Role.MANAGER && targetUser.getUserId()!=loggedInUser.getUserId()) {
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
		
		User findUser=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("usernot found"));
		
		ResponseStructure<User> responseStructure=new ResponseStructure<User>();
		responseStructure.setMessage("user found");
		responseStructure.setData(findUser);
		responseStructure.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<User>>(responseStructure,HttpStatus.OK);
		
		
	}
	public ResponseEntity<ResponseStructure<List<User>>> findAllUsers(){
		List<User> findAllUser =userRepository.findAll();
		ResponseStructure<List<User>> responseStructure=new ResponseStructure<List<User>>();
		if(findAllUser.isEmpty()) {
			throw new UserNotFoundException("no users present");
		}
		
		responseStructure.setMessage("users found");
		responseStructure.setData(findAllUser);
		responseStructure.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<List<User>>>(responseStructure,HttpStatus.OK);
		
		
	}
	
	public ResponseEntity<ResponseStructure<User>> updateUser(long userId,User user){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String username=authentication.getName();
		User loggedInUser=userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("user name not found"));
		User targetUser=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user not found"));
		if(loggedInUser.getRole()==Role.MANAGER) {
			if(targetUser.getRole()==Role.MANAGER && targetUser.getUserId()!=loggedInUser.getUserId()) {
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
		
		ResponseStructure<User> responseStructure=new ResponseStructure<User>();
		targetUser.setUsername(user.getUsername());
		targetUser.setEmail(user.getEmail());
		targetUser.setPassword(passwordEncoder.encode((user.getPassword())));
		targetUser.setPhnoNo(user.getPhnoNo());
		targetUser.setAddress(user.getAddress());
		User updatedUser=userRepository.save(targetUser);
		responseStructure.setMessage("user updated");
		responseStructure.setData(updatedUser);
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<User>>(responseStructure,HttpStatus.OK);
		
	}
	public ResponseEntity<ResponseStructure<User>> deleteUserById(long userId){
		User findUser=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("user not found"));
		ResponseStructure<User> responseStructure=new ResponseStructure<User>();
		userRepository.deleteById(findUser.getUserId());
		responseStructure.setMessage("user deleted");
		responseStructure.setData(findUser);
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<User>>(responseStructure,HttpStatus.OK);
		
	}

}
