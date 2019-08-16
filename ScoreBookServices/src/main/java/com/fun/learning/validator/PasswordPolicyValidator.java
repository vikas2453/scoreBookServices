package com.fun.learning.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterCharacteristicsRule;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RepeatCharacterRegexRule;
import org.passay.Rule;
import org.passay.RuleResult;

public class PasswordPolicyValidator implements ConstraintValidator<Password, String> {

	private static final int MIN_COMPLEX_RULES = 3;
	private static final int MIN_UPPER_CASE_CHARS = 1;
	private static final int MIN_LOWER_CASE_CHARS = 1;
	private static final int MIN_DIGIT_CASE_CHARS = 1;
	private static final int MIN_SPECIAL_CASE_CHARS = 1;
	private static final int MAX_REPETIVE_CHARS = 3;
	
	private static PasswordValidator passwordValidator; 
	
	
	public static PasswordValidator getInstance() {
		if(passwordValidator==null) {
			List<Rule>passwordRules= new ArrayList<>();
			passwordRules.add(new LengthRule(10, 128));
			CharacterCharacteristicsRule passChars=  new CharacterCharacteristicsRule(MIN_COMPLEX_RULES, 
					new CharacterRule(EnglishCharacterData.UpperCase, MIN_UPPER_CASE_CHARS),
					new CharacterRule(EnglishCharacterData.LowerCase, MIN_LOWER_CASE_CHARS),
					new CharacterRule(EnglishCharacterData.Digit, MIN_DIGIT_CASE_CHARS),
					new CharacterRule(EnglishCharacterData.Special, MIN_SPECIAL_CASE_CHARS));
			passwordRules.add(passChars);
			passwordRules.add(new RepeatCharacterRegexRule(MAX_REPETIVE_CHARS));
			passwordValidator = new PasswordValidator(passwordRules);
			return passwordValidator;
		}
			
		else
			return passwordValidator;
	}


	


	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordData passwordData = new PasswordData(password);
		PasswordValidator passwordValidator=getInstance();
		RuleResult ruleresult =passwordValidator.validate(passwordData);
		return ruleresult.isValid();
	}
}
