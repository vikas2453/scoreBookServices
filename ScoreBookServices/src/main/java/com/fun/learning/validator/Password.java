package com.fun.learning.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=PasswordPolicyValidator.class)
public @interface Password {

	String message() default "password is not valid, it must be between 10 chars and 128 Chars, with 1 upper case, 1 lower case, 1 special char with no repetivie chars";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
}
