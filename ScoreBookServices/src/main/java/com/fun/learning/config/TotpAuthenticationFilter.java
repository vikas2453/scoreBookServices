package com.fun.learning.config;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.fun.learning.exceptions.InvalidMFAVerificationCode;
import com.fun.learning.service.UserDetailsServiceJDBC;

import lombok.Data;

@Component

public class TotpAuthenticationFilter extends GenericFilterBean {
	
	@Autowired
	private UserDetailsServiceJDBC UserDetailsService;

	private final static String successUrl = "http://localhost:8200/home";

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Authentication authenticatioin = SecurityContextHolder.getContext().getAuthentication();
		String code = obtainCode(request);
		if (code == null || !requiresTotpAuthentication(authenticatioin)) {
			chain.doFilter(request, response);
			return;
		}
		if (codeIsValid(authenticatioin.getName(), code)) {
			Set<String> authorities = AuthorityUtils.authorityListToSet(authenticatioin.getAuthorities());
			authorities.remove("Role_Totp_Auth");
			authorities.add("Role_user");
			authenticatioin = new UsernamePasswordAuthenticationToken(authenticatioin.getName(),
					authenticatioin.getCredentials());
			
			SecurityContextHolder.getContext().setAuthentication(authenticatioin);
			redirectStrategy.sendRedirect((HttpServletRequest) request, (HttpServletResponse) response, successUrl);

		} else
			throw new InvalidMFAVerificationCode("Invalid Totp code");

	}

	private boolean codeIsValid(String name, String code) {
		return code != null && UserDetailsService.verifyCode(name, Integer.valueOf(code));
	}

	private boolean requiresTotpAuthentication(Authentication authenticatioin) {
		if (authenticatioin == null)
			return false;
		Set<String> authorities = AuthorityUtils.authorityListToSet(authenticatioin.getAuthorities());
		boolean hasTotpAuthority = authorities.contains("Role_Totp_Auth");
		return hasTotpAuthority && authenticatioin.isAuthenticated();
	}

	private String obtainCode(ServletRequest request) {
		return request.getParameter("");
	}

}
