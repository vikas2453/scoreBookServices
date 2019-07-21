package com.fun.learning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(97)
public class AllowAllSecurityConfig extends WebSecurityConfigurerAdapter {

	public void configure(HttpSecurity httpSecurity) throws Exception {
		//permitAll reqeusts mentioned in ant matchers
		//other request must be authenticated with form login
		httpSecurity.authorizeRequests().antMatchers("/h2/**", "/register").permitAll()
		.anyRequest().authenticated()
		.and().formLogin();

		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
	}

	/*
	 * Below method can also be used to ingore some of urls by websecurity or
	 * basically to ingore css or other static html content
	 * 
	 */

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/anyotherURLs", "/orAnyOtherStaticContent");
	}
}
