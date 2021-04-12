package com.example.Insurance.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.Insurance.model.User;

public interface UserService extends UserDetailsService{
	public UserDetails loadUserByUsername(String username);
	
	UserDTO createUser(UserDTO userDetails);

	User getUserByUserName(String username);
	

}
