package com.example.Insurance.security;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Insurance.model.User;
import com.example.Insurance.repositoy.UserRepository;



public class UserDetailsServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.getUserByUsername(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Could not find User");
		}
		return new MyUserDetails(user);
	}
  
	public User getUserByUserName(String username)
	{
		
		User user=userRepository.getUserByUsername(username);

		if(user==null) {
			throw new UsernameNotFoundException("Could not find User");
		}
		return user;
	}
	@Override
	public UserDTO createUser(UserDTO userDetails) {
		// TODO Auto-generated method stub
		
		//userDetails.setId(Long.parseLong(UUID.randomUUID().toString()));
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		ModelMapper modelmapper=new ModelMapper();
		modelmapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		User userEntity=modelmapper.map(userDetails, User.class);
		//userEntity.setEncryptedPassword("testt");
		userRepository.save(userEntity);
		
		UserDTO returnValue=modelmapper.map(userEntity, UserDTO.class);
		
		return returnValue;
	}
	
}
