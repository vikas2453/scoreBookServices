package com.fun.learning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration
@Order(99)
public class DigestSecurityConfig extends WebSecurityConfigurerAdapter {

	// Digest authentication only for customer details
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.antMatcher("/customerDetails").addFilter(digestAuthFilterBean()).exceptionHandling()
				.authenticationEntryPoint(getDigestEntryPoint()).and().authorizeRequests()
				.antMatchers("/customerDetails").hasRole("customer");
	}

	@Autowired
	public DigestAuthenticationFilter digestAuthFilterBean() throws Exception {
		DigestAuthenticationFilter digestAuthFilter = new DigestAuthenticationFilter();
		digestAuthFilter.setUserDetailsService(userDetailsServiceBean());
		digestAuthFilter.setAuthenticationEntryPoint(getDigestEntryPoint());
		return digestAuthFilter;
	}

	private DigestAuthenticationEntryPoint getDigestEntryPoint() {
		DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
		digestAuthenticationEntryPoint.setRealmName("customerDetails");
		digestAuthenticationEntryPoint.setKey("abcdef");
		return digestAuthenticationEntryPoint;
	}

	protected void configure(AuthenticationManagerBuilder authMgrBuilder) throws Exception {
		authMgrBuilder.inMemoryAuthentication().withUser("customer").password("password1").roles("customer1").and()
				.withUser("cusotmer2").password("password2").roles("customer");
	}

	
	@Autowired
	public UserDetailsService userDetailsServiceBean(UserDetailsService userDetailsService) {
		return userDetailsService;
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	

	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
