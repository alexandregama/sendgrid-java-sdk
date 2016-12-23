package com.sendgrid.api.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.sendgrid.api.email.Personalization;

public class PersonalizationTest {

	private Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void shouldIndicateAnErrorWhenPersonalizationDoesNotHaveEmailTo() throws Exception {
		Personalization personalization = new Personalization();

		Set<ConstraintViolation<Personalization>> errors = validator.validate(personalization);

		assertThat(errors.isEmpty(), is(equalTo(false)));
		assertThat(errors.size(), is(equalTo(1)));
		errors.forEach(error -> assertThat(error.getMessageTemplate(), is(equalTo("{javax.validation.constraints.NotNull.message}"))));
	}

}
