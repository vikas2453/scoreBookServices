package com.fun.learning.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fun.learning.event.UserRegistrationEvent;
import com.fun.learning.model.User;
import com.fun.learning.service.UserDetailsServiceJDBC;
import com.fun.learning.validator.UserValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RegisterController {

	@Autowired
	UserDetailsServiceJDBC userDetailService;
	@Autowired
	private UserValidator userValidator;
	private final ApplicationEventPublisher eventPublisher;

	
	/* initBinder, UserValidator and valiationPropoperties is an example of custom validator 
	 * However this is not required as we can use Hibernate validator along with JSR 303( Specification for validation) 
	 */ 
	
	/*@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		// Form target
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		log.debug("Target=" + target);

		if (target.getClass() == User.class) {
			dataBinder.setValidator(userValidator);
		}
		// ...
	}*/

	@GetMapping("/register")
	public ModelAndView register() {
		ModelAndView model = new ModelAndView();
		User user = new User();
		model.addObject(user);		
		return model;
	}
	
	@GetMapping("/registerTest")
	public String registerTest() {
		return "This requires authentication";
	}
	

	
	
	@PostMapping("/register")
	public ModelAndView addUser(ModelAndView model, 
			@Valid @ModelAttribute("user")  User user, //
	         BindingResult result ) {
		
		if (result.hasErrors()) {	        
	         return model;
	      }		
		user.setAccountNonLocked(true);
		user.setAccountNonExpired(true);
		user.setCredentialsNonExpired(true);
		userDetailService.addUser(user);
		try {
			eventPublisher.publishEvent(new UserRegistrationEvent(user));
		}
		catch(Exception ex) {
			user.setEnabled(true);
			log.debug("publishing event failed", ex);
		}
		model.setViewName("registerSuccessful");
		return model;

	}


	/*private void copyUser(User userm, @Valid UserDto user) {
		log.debug("now copying");
		userm.setUsername(user.getUsername());
		userm.setEmail(user.getEmail());
		userm.setPassword(user.getPassword() );
		userm.setFirstName(user.getFirstName());
		userm.setLastName(user.getLastName());
		userm.setGender(user.getGender());
		
	}
*/
	/*@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String viewRegister(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "register";
	}*/

}
