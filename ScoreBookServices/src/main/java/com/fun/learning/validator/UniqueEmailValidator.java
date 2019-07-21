package com.fun.learning.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fun.learning.repo.UserRepo;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>{
	
	@Autowired
	UserRepo userRepo;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value!=null && userRepo.findByEmail(value)==null;
	}

}
