package com.fun.learning.event;

import org.springframework.context.ApplicationEvent;

import com.fun.learning.model.User;

import lombok.Getter;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {
	private final User user;

	public UserRegistrationEvent(User user) {
		super(user);
		this.user=user;
		
	}

}
