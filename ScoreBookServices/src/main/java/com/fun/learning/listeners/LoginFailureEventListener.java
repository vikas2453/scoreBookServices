package com.fun.learning.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
//import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.fun.learning.model.User;
import com.fun.learning.repo.UserRepo;
import com.fun.learning.service.UserDetailsServiceJDBC;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Data
@Slf4j
@RequiredArgsConstructor
public class LoginFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
		
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserDetailsServiceJDBC userDetailsService;
	
	// we can put this in some configuration file
	private static final int maxLoginFailedAttempt =3;
	
	
	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		log.debug("Authentication Failure event happened");
		
		String userName = (String)event.getAuthentication().getPrincipal();		
		User user= userRepo.findByUsername(userName);
		if(user!=null ){
			if(user.getLoginFailedAttempt()<maxLoginFailedAttempt)
				userDetailsService.updateLoginAttempt(user,false);
			else
				user.setAccountNonLocked(false);
		}
		
	}

}
