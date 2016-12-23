package com.sendgrid.internal.validator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class BeanValidatorFactory {

	private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	public static Validator getValidator() {
		return factory.getValidator();
	}

}
