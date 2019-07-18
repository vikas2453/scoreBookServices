package com.fun.learning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(97)
public class AllowAllSecurityConfig  extends WebSecurityConfigurerAdapter{

	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.antMatcher("/h2/**").authorizeRequests().anyRequest().permitAll();
				
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/user", "/furtheurls");
	}
}
