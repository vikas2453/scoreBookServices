package com.fun.learning.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(97)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static List<String> clients = Arrays.asList("google", "facebook");

	@Autowired
	private AdditionAuthenticationProvider additionAuthenticationProvider;

	@Autowired
	@Qualifier("oAuth2successHandler")
	private OAuth2AuthenticationSuccessHandler oAuth2successHandler;
	
	@Autowired
	private TotpAuthenticationFilter totpAuthenticationFilter;

	public void configure(HttpSecurity httpSecurity) throws Exception {
		//@formatter:off
		httpSecurity.addFilterBefore(totpAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).
		// permitAll reqeusts mentioned in ant matchers		
		authorizeRequests().antMatchers("/h2/**", "/register", "/login", "/qrCode").permitAll()
		// Any request must be authenticated with form login
		//.anyRequest().authenticated()
		//Any request with role user will have access to all the pages of sites
		
		.antMatchers("totp-login", "totp-login-error").hasAnyRole("Totp_Auth")
		.anyRequest().hasRole("user")
		.and().formLogin().loginPage("/login").successHandler(new AuthenticationSuccessHandlerImlp()).failureUrl("/login-error")
		.authenticationDetailsSource(new AdditionalAuthenticationDetailSource()).and().logout()
		
		// OAuth2LoginPage and it successhandler
		.and().oauth2Login().loginPage("/login").successHandler(oAuth2successHandler);

		// this line though enables us to view h2 database, however it is dangerous as
		// it can result in crossSiteForgeryProtection completely
		// and exposing our website for crosssite attacks in which action takes place on
		// target site instead of malacious site as browser while senind the request
		// takes cookie information for domain and submits the request with that cookie.
		// Server checks the cookie and think that request is valid.
		// User doesn't know the domain he is making the request as this form is embeded
		// on a malacious site, however action of the form in malacious site is target
		// site domain.
		httpSecurity.csrf().disable();

		// httpSecurity.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		// below line disables the frameoptions completely so is more dangerous than
		// next line which allows framing from the same origin only.
		// httpSecurity.headers().frameOptions().disable();
		httpSecurity.headers().frameOptions().sameOrigin();
		//@formatter:on
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

	

	@Bean
	@Autowired
	public DaoAuthenticationProvider getDaoAuthenticationProvider(
			@Qualifier("myPasswordEncoder") PasswordEncoder passwordEncoder,
			UserDetailsService userDetailsServiceJDBC) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(userDetailsServiceJDBC);
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authBuilder) {
		authBuilder.authenticationProvider(additionAuthenticationProvider);
	}

	/*@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		List<ClientRegistration> registrations = clients.stream().map(c -> getRegistration(c))
				.filter(registration -> registration != null).collect(Collectors.toList());

		return new InMemoryClientRegistrationRepository(registrations);
	}*/
}
