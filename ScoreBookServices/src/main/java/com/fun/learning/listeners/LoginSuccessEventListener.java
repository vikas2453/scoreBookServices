package com.fun.learning.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//import org.springframework.security.access.event.AuthorizedEvent;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.fun.learning.model.User;
import com.fun.learning.repo.UserRepo;
import com.fun.learning.service.UserDetailsServiceJDBC;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

	
	@Autowired
	private UserDetailsServiceJDBC userDetailsService;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {

		User user = (User) event.getAuthentication().getPrincipal();

		if (user.getLoginFailedAttempt() > 0) {
			log.debug("updating login attempt for user:{})", user.getUsername());
			userDetailsService.updateLoginAttempt(user, true);
		}

	}

}
