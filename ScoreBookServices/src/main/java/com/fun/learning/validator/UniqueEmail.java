package com.fun.learning.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=UniqueEmailValidator.class)
public @interface UniqueEmail {
	
	String message() default "email already exist";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};

}
