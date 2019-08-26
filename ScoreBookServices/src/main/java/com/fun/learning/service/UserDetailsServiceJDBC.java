package com.fun.learning.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fun.learning.exceptions.InvalidMFAVerificationCode;
import com.fun.learning.model.Authority;
import com.fun.learning.model.User;
import com.fun.learning.repo.UserRepo;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
// There can be different implementation of userDetailsService like LDAP of from other identity store. 
public class UserDetailsServiceJDBC implements UserDetailsService  {
	
	private final static String ISSUER="ScoreBookService";
	
	@Autowired
	@Qualifier("myPasswordEncoder")
	private PasswordEncoder passwordEncoder;

	@Autowired
	private  UserRepo userRepo;
	
	
	private static GoogleAuthenticator googleAuth = new GoogleAuthenticator();

	@Override
	@Transactional
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user =userRepo.findByUsername(username);
		 if(user==null)
			 throw new UsernameNotFoundException(username);
		 Set<String> authorities = AuthorityUtils.authorityListToSet(user.getAuthorities());
		 //List<Authority> authorities = new ArrayList<>();
		 if(user.isMFAEnabled()) {
			 if(!authorities.contains("ROLE_Totp_Auth"))
			 user.getAuthorityList() .add(new Authority("ROLE_Totp_Auth", user));
		 }
		 else {
			 if(!authorities.contains("ROLE_user"))
				 user.getAuthorityList() .add(new Authority("ROLE_user", user));
		 }
		
		 return user;
	}
	
	@Transactional
	public void updateLoginAttempt(User user, Boolean success) {		 
		if (success) {
			user.setLoginFailedAttempt(0);
		} else {
			user.setLoginFailedAttempt(user.getLoginFailedAttempt() + 1);
		}
		userRepo.saveAndFlush(user);
	}
	
	public boolean userIdTaken(String userId) {
		return (userRepo.getOne(userId)!=null);		
		
	}

	public boolean addUser(User user) {		
		user.setPassword(passwordEncoder .encode(user.getPassword()));
		user.setPin(passwordEncoder.encode(user.getPin()));
		 if(userRepo.save(user)!=null)
			 return true;
		 return false;
		
	}

	public boolean isMFAEnabled(String  username) {
		User user = loadUserByUsername(username);
		return user.isMFAEnabled();
	}

	public String generateNewGoogleAuthQRUrl(String username) {
		GoogleAuthenticatorKey  authKey= googleAuth.createCredentials();
		String secret = authKey.getKey();
		User user= loadUserByUsername(username);
		user.setSecret(secret);
		return GoogleAuthenticatorQRGenerator.getOtpAuthURL(ISSUER, username, authKey);
	}
	
	public void enableMFAforUser(String  username, int code) {
		if(!verifyCode(username, code)) {
			throw new InvalidMFAVerificationCode("Verification code is invalid");
		}
		User user = loadUserByUsername(username);
		user.setMFAEnabled(true);
	}

	public  boolean verifyCode(String username, int code) {
		User user = loadUserByUsername(username);
		return googleAuth.authorize(user.getSecret(), code);
	}
}
