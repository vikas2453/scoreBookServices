package com.fun.learning.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fun.learning.model.User;

@Controller
public class UserLoginController {

	@GetMapping(value = "/login")
	public String showLoginForm(ModelAndView model, String error, String logout) {
		
		User user = new User();
		model.addObject(user);	

		return "login";

	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.POST)
	public String loginerror(ModelMap model) {
		model.addAttribute("error", "true");
		return "userLogin";
	}

}
