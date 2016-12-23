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

import com.sendgrid.api.email.EmailInformation;

public class EmailPayloadTest {

	private Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void shouldThrowsAnExceptionWhenEmailPayloadDoesNotHaveAnEmail() throws Exception {
		EmailInformation email = EmailInformation
				.newEmail()
				.withNameTo("Alexandre Gama")
				.build();

		Set<ConstraintViolation<EmailInformation>> errors = validator.validate(email);
		System.out.println(errors);

		assertThat(errors.isEmpty(), is(false));
		assertThat(errors.size(), is(equalTo(1)));
		errors.forEach(error -> assertThat(error.getMessageTemplate(), is(equalTo("{javax.validation.constraints.NotNull.message}"))));
	}


}
