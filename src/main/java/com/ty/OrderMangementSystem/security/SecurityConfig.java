package com.ty.OrderMangementSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ty.OrderMangementSystem.jwt.JwtAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;
	@Autowired
	private CustomAuthenticationEntryPoint authenticationEntryPoint;
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider(customUserDetailsService);
		provider.setPasswordEncoder(getPasswordEncoder());
		return provider;
		
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		   .csrf(csrf->csrf.disable())
		   .authorizeHttpRequests(auth->auth
				   .requestMatchers("/auth/**").permitAll()
//				   .requestMatchers("/admin/**").hasRole("ADMIN")
//				   .requestMatchers("/manager/**").hasAnyRole("ADMIN","MANAGER")
//				   .requestMatchers("/user/**").hasAnyRole("ADMIN","CUSTOMER","MANAGER")
				   .anyRequest().authenticated()
				   )
		   .sessionManagement(session->session
				   .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				   )
		   .exceptionHandling(exception ->exception
		                     .authenticationEntryPoint(authenticationEntryPoint)
	                         .accessDeniedHandler(accessDeniedHandler)
	    )
		   .authenticationProvider(authenticationProvider())
		 .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		   
		return http.build();
	}
		
	

}
