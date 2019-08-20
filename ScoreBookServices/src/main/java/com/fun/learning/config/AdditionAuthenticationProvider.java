package com.fun.learning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fun.learning.model.User;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Component

public class AdditionAuthenticationProvider extends DaoAuthenticationProvider {

	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) {
		super.additionalAuthenticationChecks(userDetails, authentication);
		
		User user = (User)userDetails;
		
		AdditionalAuthenticationDetails additionalAuthenticationDetails = (AdditionalAuthenticationDetails)authentication.getDetails();
		
		String presentedPin=additionalAuthenticationDetails.getSecurityPin();
		
		if (!getPasswordEncoder().matches(presentedPin, user.getPin())) {
			logger.debug("Authentication failed: password does not match stored value");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}
		user.setPin(null);

	}

	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}

	
	@Autowired
	@Qualifier("myPasswordEncoder")
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		super.setPasswordEncoder(passwordEncoder);
	}

}
