package com.ty.OrderMangementSystem.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	 @Value("${jwt.secretKey}")
	 private String secretKey;
	 
	 @Value("${jwt.expiration}")
	 private Long jwtExpiration;
	 
	 public SecretKey getSecretKey() {
			return Keys.hmacShaKeyFor(secretKey.getBytes());
			
		}
	 public String generateToken(UserDetails userDetails,HashMap<String, Object>extractClaims) {
			return Jwts
					.builder()
					.claims(extractClaims)
					.subject(userDetails.getUsername())
					.issuedAt(new Date(System.currentTimeMillis()))
					.expiration(new Date(System.currentTimeMillis()+jwtExpiration))
					.signWith(getSecretKey())
					.compact();
			
		}
	
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public  <T> T extractClaim(String token,Function<Claims, T> viewResolver) {
		Claims claim=extractAllClaims(token);
		return viewResolver.apply(claim);
	}
	
	public Claims extractAllClaims(String token) {
		return
				Jwts
				    .parser()
				    .verifyWith(getSecretKey())
				    .build()
				    .parseSignedClaims(token)
				    .getPayload();
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(userDetails,new HashMap<>());
	}
	
	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String username=extractUserName(token);
		return userDetails.getUsername().equals(username) && !isTokenExpired(token);
		
		
	}
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
		
	}
	

}
