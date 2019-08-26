package com.fun.learning.config;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthenticationSuccessHandlerImlp implements AuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if(requiesTotpAuthentication(authentication))
			redirectStrategy.sendRedirect(request, response, "/totp-login");
		else
			redirectStrategy.sendRedirect(request, response, "/home");
	}

	private boolean requiesTotpAuthentication(Authentication authentication) {
		if (authentication == null)
			return false;
		Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		boolean hasTotpAuthority = authorities.contains("Role_Totp_Auth");
		return hasTotpAuthority && authentication.isAuthenticated();
	}

}
