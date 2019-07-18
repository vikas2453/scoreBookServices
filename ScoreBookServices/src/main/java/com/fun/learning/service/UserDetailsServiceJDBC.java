package com.fun.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mindtree.poc.JcachePoc.model.User;
import com.mindtree.poc.JcachePoc.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
// There can be different implementation of userDetailsService like LDAP of from other identity store. 
public class UserDetailsServiceJDBC implements UserDetailsService  {
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	UserRepo userRepo;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username);
	}
		
	public void updateLoginAttempt(User user, Boolean success) {		 
		if (success) {
			user.setLoginFailedAttempt(0);
		} else {
			user.setLoginFailedAttempt(user.getLoginFailedAttempt() + 1);
		}
	}
	
	public boolean userIdTaken(String userId) {
		return (userRepo.getOne(userId)!=null);		
		
	}

	public boolean addUser(User user) {		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		 if(userRepo.save(user)!=null)
			 return true;
		 return false;
		
	}
}
