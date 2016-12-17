package com.sendgrid.api.client;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sendgrid.api.client.SendGridApiKey;
import com.sendgrid.api.email.EmailContent;
import com.sendgrid.api.email.EmailInformation;
import com.sendgrid.api.email.EmailPayload;
import com.sendgrid.api.email.Personalization;
import com.sendgrid.api.email.EmailContent.EmailMimeType;
import com.sendgrid.internal.email.DefaultEmailClientApi;
import com.sendgrid.internal.exception.SendGridException;

public class EmailSenderTest {

	private DefaultEmailClientApi client;

	@Before
	public void setUp() {
		SendGridApiKey key = new SendGridApiKey("YOUR_KEY");
		client = new DefaultEmailClientApi(key);
	}
	
	@Test
	public void shouldSendAnEmailToSendGrid() throws Exception {
		EmailPayload payload = createEmailPayload();
		
		client.emailApi().sendEmail(payload);
	}
	
	@Test(expected = SendGridException.class)
	public void shouldThrowsAnExceptionWhenTryingToSendAnEmailWithoutEmailTo() throws Exception {
		EmailPayload payload = createEmailWithoutEmailToPayload();
		
		client.emailApi().sendEmail(payload);
	}
	
	@Test
	public void shouldThrowsAnExceptionWithSendGridErrorsWhenTryingToSendAnEmailWithoutEmailTo() throws Exception {
		EmailPayload payload = createEmailWithoutEmailToPayload();
		
		try {
			client.emailApi().sendEmail(payload);
		} catch (SendGridException e) {
			System.out.println(e.getErrors());
		}
	}

	@Test
	public void shouldCreateAValidJsonPayload() throws Exception {
		EmailPayload payload = createEmailPayload();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		StringWriter out = new StringWriter();
		objectMapper.writeValue(out, payload);
		
		System.out.println(out);
	}
	
	private EmailPayload createEmailPayload() {
		EmailPayload payload = new EmailPayload();
		EmailInformation emailFrom = new EmailInformation();
		emailFrom.setEmail("alexandre.gama.lima@gmail.com");
		payload.setEmailFrom(emailFrom);

		Personalization personalization = new Personalization();
		
		EmailInformation emailTo = new EmailInformation();
		emailTo.setEmail("alexandre.gama@elo7.com");
		emailTo.setName("Gama");
		personalization.addEmailTo(emailTo);
		
		payload.addPersonalization(personalization);
		
		payload.setSubject("Its working man!");
		
		EmailContent content = new EmailContent();
		content.setType(EmailMimeType.TEXT_HTML);
		content.setValue("Its really awesome");
		payload.addContent(content);
		return payload;
	}
	
	private EmailPayload createEmailWithoutEmailToPayload() {
		EmailPayload payload = new EmailPayload();
		EmailInformation emailFrom = new EmailInformation();
		emailFrom.setEmail("alexandre.gama.lima@gmail.com");
		payload.setEmailFrom(emailFrom);
		
		Personalization personalization = new Personalization();
		
		EmailInformation emailTo = new EmailInformation();
//		emailTo.setEmail("alexandre.gama@elo7.com");
		emailTo.setName("Gama");
		personalization.addEmailTo(emailTo);
		
		payload.addPersonalization(personalization);
		
		payload.setSubject("Its working man!");
		
		EmailContent content = new EmailContent();
		content.setType(EmailMimeType.TEXT_HTML);
		content.setValue("Its really awesome");
		payload.addContent(content);
		return payload;
	}
	
}
