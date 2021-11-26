package com.cleverit.testdevelop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SkuValidator implements ConstraintValidator<SkuConstraint, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value.startsWith("FAL-")) {
			String numberValue = value.split("FAL-")[1];
			Pattern numberPattern = Pattern.compile("^\\d+$");
			Matcher numberMatcher = numberPattern.matcher(numberValue);

			return numberMatcher.find() && Integer.parseInt(numberValue) >= 1000000 &&
					Integer.parseInt(numberValue) <= 99999999;
		}
		return false;
	}

}
