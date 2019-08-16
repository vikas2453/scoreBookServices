package com.fun.learning.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	
	@RequestMapping("/")
	public String getHomeMessage() {
		 return "This is home page and requires authentication";
	}
}
