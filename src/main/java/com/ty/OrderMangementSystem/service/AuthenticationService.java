package com.ty.OrderMangementSystem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ty.OrderMangementSystem.Exception.UserExistException;
import com.ty.OrderMangementSystem.Repository.UserRepository;
import com.ty.OrderMangementSystem.dto.LoginRequestDto;
import com.ty.OrderMangementSystem.dto.LoginResponseDto;
import com.ty.OrderMangementSystem.dto.RegisterRequestDto;
import com.ty.OrderMangementSystem.dto.RegisterResponseDto;
import com.ty.OrderMangementSystem.entity.Role;
import com.ty.OrderMangementSystem.entity.User;
import com.ty.OrderMangementSystem.jwt.JwtService;
import com.ty.OrderMangementSystem.security.CustomUserDetails;
import com.ty.OrderMangementSystem.util.ResponseStructure;
import com.ty.OrderMangementSystem.util.ResponseUtil;

@Service
public class AuthenticationService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private  AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	
	public ResponseEntity<ResponseStructure<RegisterResponseDto>> registerCustomer(RegisterRequestDto registerRequestDto){
		if(userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
			throw new UserExistException("customer already present,please login");
		}
		Role role=Role.CUSTOMER;
		User user=new User();
		user.setRole(role);
		user.setUsername(registerRequestDto.getUsername());
		user.setEmail(registerRequestDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
		User register=userRepository.save(user);
		RegisterResponseDto registerResponseDto=new RegisterResponseDto();
		registerResponseDto.setUsername(register.getUsername());
		registerResponseDto.setEmail(register.getEmail());
		registerResponseDto.setRole(register.getRole());
		
		return ResponseUtil.buildResponse(HttpStatus.CREATED, "register successfully", registerResponseDto);
		
	}
	public ResponseEntity<ResponseStructure<RegisterResponseDto>> registerAdmin(RegisterRequestDto registerRequestDto){
		if(userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
			throw new UserExistException("Admin already present,please login");
		}
		Role role=Role.ADMIN;
		User user=new User();
		user.setRole(role);
		user.setUsername(registerRequestDto.getUsername());
		user.setEmail(registerRequestDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
		User register=userRepository.save(user);
		RegisterResponseDto registerResponseDto=new RegisterResponseDto();
		registerResponseDto.setUsername(register.getUsername());
		registerResponseDto.setEmail(register.getEmail());
		registerResponseDto.setRole(register.getRole());
		
		
		return ResponseUtil.buildResponse(HttpStatus.CREATED, "register successfully", registerResponseDto);
		
	}
	public ResponseEntity<ResponseStructure<RegisterResponseDto>> registerManager(RegisterRequestDto registerRequestDto){
		if(userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
			throw new UserExistException("manager already present,please login");
		}
		Role role=Role.MANAGER;
		User user=new User();
		user.setRole(role);
		user.setUsername(registerRequestDto.getUsername());
		user.setEmail(registerRequestDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
		User register=userRepository.save(user);
		RegisterResponseDto registerResponseDto=new RegisterResponseDto();
		registerResponseDto.setUsername(register.getUsername());
		registerResponseDto.setEmail(register.getEmail());
		registerResponseDto.setRole(register.getRole());
		
		
		return ResponseUtil.buildResponse(HttpStatus.CREATED, "register successfully", registerResponseDto);
		
	}
	public ResponseEntity<ResponseStructure<LoginResponseDto>> loginUser(LoginRequestDto loginRequestDto){
	
		Authentication authentication =authenticationManager.authenticate(
				 new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
				);
		CustomUserDetails userDetails=(CustomUserDetails) authentication.getPrincipal();
		String token =jwtService.generateToken(userDetails);
		LoginResponseDto loginResponseDto=new LoginResponseDto();
		loginResponseDto.setMessage("user logged in successfully");
		loginResponseDto.setUserName(userDetails.getUsername());
		loginResponseDto.setRole(userDetails.getRole());
		loginResponseDto.setToken(token);
		return ResponseUtil.buildResponse(HttpStatus.OK, "login successful", loginResponseDto);
	}
	
	
	

}
