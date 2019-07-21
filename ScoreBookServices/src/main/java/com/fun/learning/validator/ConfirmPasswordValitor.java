package com.fun.learning.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fun.learning.model.User;

public class ConfirmPasswordValitor implements ConstraintValidator<ConfirmPassword, User> {

	@Override
	public boolean isValid(User user, ConstraintValidatorContext context) {
		return user != null && user.getPassword() != null && user.getConfirmPassword() != null
				&& (user.getPassword().equals(user.getConfirmPassword()));
	}

}
