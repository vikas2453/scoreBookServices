package com.fun.learning.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class AdditionalAuthenticationDetailSource extends WebAuthenticationDetailsSource {
	
	public AdditionalAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new AdditionalAuthenticationDetails(context);
	}

}
