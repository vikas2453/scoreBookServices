package com.fun.learning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(97)
public class AllowAllSecurityConfig extends WebSecurityConfigurerAdapter {

	public void configure(HttpSecurity httpSecurity) throws Exception {
		//permitAll reqeusts mentioned in ant matchers
		//other request must be authenticated with form login
		httpSecurity.authorizeRequests().antMatchers("/h2/**", "/register").permitAll()
		.anyRequest().authenticated()
		.and().formLogin();
		
		// this line though enables us to view h2 database, however it is dangerous as it can result in crossSiteForgeryProtection completely
		// and exposing our website for crosssite attacks in which action takes place on target site instead of malacious site as browser while senind the request
		// takes cookie information for domain and submits the request with that cookie. Server checks the cookie and think that request is valid. 
		// User doesn't know the domain he is making the request as this form is embeded on a malacious site, however action of the form in malacious site is target site domain. 
		httpSecurity.csrf().disable();
		
		
		//httpSecurity.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		// below line disables the frameoptions completely so is more dangerous than next line which allows framing from the same origin only. 
		//httpSecurity.headers().frameOptions().disable();
		httpSecurity.headers().frameOptions().sameOrigin();
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
	
	@Bean(name="myPasswordEncoder")
	public PasswordEncoder getPasswordEncoder() {
		DelegatingPasswordEncoder delPasswordEncoder=  (DelegatingPasswordEncoder)PasswordEncoderFactories.createDelegatingPasswordEncoder();
		BCryptPasswordEncoder bcryptPasswordEncoder =new BCryptPasswordEncoder();
		delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
		return delPasswordEncoder;		
	}
	
	@Bean
	@Autowired	
	public DaoAuthenticationProvider getDaoAuthenticationProvider(@Qualifier("myPasswordEncoder") PasswordEncoder passwordEncoder, UserDetailsService userDetailsServiceJDBC) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(userDetailsServiceJDBC);
		return daoAuthenticationProvider;
	}
}
