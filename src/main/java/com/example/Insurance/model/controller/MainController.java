package com.example.Insurance.model.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String home()
	{
		return "forward:/index.html";
		
	}

}
