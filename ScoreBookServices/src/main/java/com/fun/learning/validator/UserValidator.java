package com.fun.learning.validator;

import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fun.learning.model.User;
import com.fun.learning.repo.UserRepo;

@Component
public class UserValidator implements Validator {

	// common-validator library.
	private EmailValidator emailValidator = EmailValidator.getInstance();

	@Autowired
	private UserRepo userRepo;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == User.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		// Check the fields of User.
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.user.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.user.firstName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.user.lastName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.user.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.user.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.user.confirmPassword");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.user.gender");

		if (!this.emailValidator.isValid(user.getEmail())) {
			// Invalid email.
			errors.rejectValue("email", "Pattern.user.email");
		} else if (userRepo.findByEmail(user.getEmail()) != null) {

			// Email has been used by another account.
			errors.rejectValue("email", "Duplicate.user.email");
		}

		if (!errors.hasFieldErrors("userName")) {
			if (userRepo.findByUsername(user.getUsername()) != null)
				// Username is not available.
				errors.rejectValue("username", "Duplicate.user.username");
		}

		if (!errors.hasErrors()) {
			if (!user.getConfirmPassword().equals(user.getPassword())) {
				errors.rejectValue("confirmPassword", "Match.user.confirmPassword");
			}
		}

	}
}
