package com.fun.learning.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=ConfirmPasswordValitor.class)
public @interface ConfirmPassword {
	String message() default "password and confirm password doesn't match";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};

}
