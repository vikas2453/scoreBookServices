package com.fun.learning.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fun.learning.model.User;
import com.fun.learning.repo.UserRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailVerifyController {
	
	private final UserRepo userRepo;
	
	@PostMapping("/verify/email")
	public ModelAndView addUser(ModelAndView model, 
			String username,  //
	         BindingResult result ) {
		
		User user= userRepo.findByUsername(username);
		if(user!=null)		
			model.setViewName("registerSuccessful");
		return model;

	}

}
