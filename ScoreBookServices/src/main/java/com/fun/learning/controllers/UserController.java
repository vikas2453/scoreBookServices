package com.fun.learning.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mindtree.poc.JcachePoc.model.User;
import com.mindtree.poc.JcachePoc.service.UserDetailsServiceJDBC;

@Controller
public class UserController {
	
	@Autowired
	UserDetailsServiceJDBC userDetailService;
	
	
	@GetMapping("/register")
	public ModelAndView addUser() {
		ModelAndView model = new ModelAndView();
		User user = new User();
		model.addObject(user);
		return model;
		
	}

}
