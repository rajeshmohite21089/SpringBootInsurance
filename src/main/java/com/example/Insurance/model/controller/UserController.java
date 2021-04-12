package com.example.Insurance.model.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Insurance.model.CreateUserRequestModel;
import com.example.Insurance.model.CreateUserResponseModel;
import com.example.Insurance.repositoy.UserRepository;
import com.example.Insurance.security.UserDTO;
import com.example.Insurance.security.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	@Lazy
	private UserService userServices;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping(
			consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}
			)
	public ResponseEntity<CreateUserResponseModel> createUser( @RequestBody CreateUserRequestModel userDetails) {

		ModelMapper modelmapper = new ModelMapper();
		modelmapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDTO = modelmapper.map(userDetails, UserDTO.class);
		UserDTO createdUser = userServices.createUser(userDTO);
		CreateUserResponseModel createUserResponseModel = modelmapper.map(createdUser, CreateUserResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseModel);
	}
	
}
