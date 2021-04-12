package com.example.Insurance.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Insurance.model.LoginRequestModel;
import com.example.Insurance.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private UserService userService;
	private Environment environment;

	 @Bean public UserService userDetailsService() { return new
			 UserDetailsServiceImpl(); }
	
	public AuthenticationFilter(UserService userDetailsServiceImpl,AuthenticationManager authenticationManager,Environment environment)
	{
		this.userService=userDetailsServiceImpl;
		this.environment=environment;
		super.setAuthenticationManager(authenticationManager);

	}
	

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
	{
		
		try {
			LoginRequestModel	creds = new ObjectMapper().
					readValue(request.getInputStream(), LoginRequestModel.class);
		
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						creds.getUsername(),
						creds.getPassword(),
						new ArrayList<>())
				);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String username=((UserDetails)authResult.getPrincipal()).getUsername();
		
		UserDetails userDetails=userService.loadUserByUsername(username);
		
		User user=userService.getUserByUserName(username);
		
		           System.out.println("userDetails.getAuthorities()"+userDetails.getAuthorities()); 

		           System.out.println("user id"+user.getId());
		          
		           //response.setContentType("application/json");
		          // response.setCharacterEncoding("UTF-8");
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
		String token=Jwts.builder().
			     setSubject(userDetails.getUsername()).
			     setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(environment.getProperty("token.expiration.time")))).
			     signWith(SignatureAlgorithm.HS512,environment.getProperty("token.secrete"))
			     .compact();
	            
	response.setHeader("token", token);
	response.setHeader("userId", userDetails.getUsername());
    response.getWriter().write(user.getId() +":"+token+":"+userDetails.getAuthorities());
  

    response.flushBuffer();
	ResponseEntity.ok(userDetails);
		// TODO Auto-generated method stub
}
}
