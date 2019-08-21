package com.fun.learning.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fun.learning.model.User;
import com.fun.learning.service.UserDetailsServiceJDBC;

import lombok.Data;

@Controller
@Data
public class UserLoginController {
	
	private UserDetailsServiceJDBC userDetailsService;
	
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
	
	@GetMapping(value="/account")
	public String getAccount(Model model, Principal principal) {
		boolean userHasMFAEnabled = ( userDetailsService).isMFAEnabled(principal.getName());
		model.addAttribute("mfaEnabled", userHasMFAEnabled);
		return "account";
	}
	
	@GetMapping(value="/authenticator-url")
	public String getGoogleAuthenticatorQRUrl(Model model, Principal principal) {
		boolean userHasMFAEnabled = ( userDetailsService).isMFAEnabled(principal.getName());
		if(userHasMFAEnabled) {
			model.addAttribute("qrUrl", userDetailsService.generateNewGoogleAuthQRUrl(principal.getName()));
		}
		model.addAttribute("mfaEnabled", userHasMFAEnabled);
		return "account";
	}

}
