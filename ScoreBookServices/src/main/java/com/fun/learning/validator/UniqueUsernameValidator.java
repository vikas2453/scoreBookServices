package com.fun.learning.validator;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.fun.learning.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
	
	@Autowired
	UserRepo userRepo;

	@Override
	@Transactional
	public boolean isValid(String value, ConstraintValidatorContext context) {
		log.info(" value of email :"+ value);
		return value!=null && userRepo.findByUsername(value)==null;
			
	}

}
