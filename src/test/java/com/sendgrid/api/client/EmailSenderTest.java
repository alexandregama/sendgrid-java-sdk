package com.sendgrid.api.client;

import static javax.ws.rs.client.Entity.json;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static javax.ws.rs.core.Response.Status.Family.SERVER_ERROR;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.StringWriter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sendgrid.api.email.EmailContent;
import com.sendgrid.api.email.EmailContent.EmailMimeType;
import com.sendgrid.api.email.EmailInformation;
import com.sendgrid.api.email.EmailPayload;
import com.sendgrid.api.email.Personalization;
import com.sendgrid.internal.email.DefaultEmailClientApi;
import com.sendgrid.internal.exception.SendGridError;
import com.sendgrid.internal.exception.SendGridException;

@RunWith(MockitoJUnitRunner.class)
public class EmailSenderTest {

	private static final String SENDGRID_API_SEND_EMAIL = "https://api.sendgrid.com/v3/mail/send";

	private DefaultEmailClientApi client;

	private EmailClientApi clientApi;

	private SendGridApiKey whateverKey = new SendGridApiKey("whatever_key");
	@Mock
	private Client restClient;
	@Mock
	private WebTarget webTarget;
	@Mock
	private Builder invocationBuilder;
	@Mock
	private StatusType statusType;
	@Mock
	private Response response;

	@Before
	public void setUp() {
		SendGridApiKey key = new SendGridApiKey("YOUR_KEY");
		client = DefaultEmailClientApi.withSecretKey(key).getApiAccess();
		clientApi = DefaultEmailClientApi.withSecretKey(whateverKey).overridingTheDefaultRestClientWith(restClient);
	}

	@Test
	public void shouldSendAnEmailToSendGrid() throws Exception {
		EmailPayload payload = createEmailWithValidPayload();

		when(restClient.target(SENDGRID_API_SEND_EMAIL)).thenReturn(webTarget);
		when(webTarget.request(APPLICATION_JSON)).thenReturn(invocationBuilder);
		when(invocationBuilder.header("Authorization", "Bearer " + whateverKey.getApiKey())).thenReturn(invocationBuilder);
		when(invocationBuilder.post(json(payload))).thenReturn(response);
		when(response.getStatusInfo()).thenReturn(OK);

		clientApi.emailApi().sendEmail(payload);

		verify(restClient).target("https://api.sendgrid.com/v3/mail/send");
	}

	@Test
	public void shouldSendARealEmailToSendGrid() throws Exception {
		EmailPayload payload = createEmailWithValidPayload();

		client.emailApi().sendEmail(payload);
	}

	@Test
	public void shouldThrowsAnUnauthorizedExceptionWithSendGridErrorsWhenUserIsNotAuthorized() throws Exception {
		SendGridApiKey invalidKey = new SendGridApiKey("INVALID_KEY");
		client = DefaultEmailClientApi.withSecretKey(invalidKey).getApiAccess();
		EmailPayload payload = createEmailWithValidPayload();

		try {
			client.emailApi().sendEmail(payload);
		} catch (SendGridException e) {
			SendGridError sendGridError = e.getSendGridErrors().getTypedErrors().get(0);
			assertThat(sendGridError.getMessage(), equalTo("The provided authorization grant is invalid, expired, or revoked"));
		}
	}

	@Test(expected = SendGridException.class)
	public void shouldThowsAnSendGridExceptionWhenResponseCodeIsBadRequest() throws Exception {
		EmailPayload payload = createEmailWithValidPayload();

		when(restClient.target(SENDGRID_API_SEND_EMAIL)).thenReturn(webTarget);
		when(webTarget.request(APPLICATION_JSON)).thenReturn(invocationBuilder);
		when(invocationBuilder.header("Authorization", "Bearer " + whateverKey.getApiKey())).thenReturn(invocationBuilder);
		when(invocationBuilder.post(json(payload))).thenReturn(response);
		when(response.getStatusInfo()).thenReturn(BAD_REQUEST);

		clientApi.emailApi().sendEmail(payload);
	}

	@Test(expected = SendGridException.class)
	public void shouldThowsAnSendGridExceptionWhenResponseCodeIsUnauthorized() throws Exception {
		EmailPayload payload = createEmailWithValidPayload();

		when(restClient.target(SENDGRID_API_SEND_EMAIL)).thenReturn(webTarget);
		when(webTarget.request(APPLICATION_JSON)).thenReturn(invocationBuilder);
		when(invocationBuilder.header("Authorization", "Bearer " + whateverKey.getApiKey())).thenReturn(invocationBuilder);
		when(invocationBuilder.post(json(payload))).thenReturn(response);
		when(response.getStatusInfo()).thenReturn(UNAUTHORIZED);

		clientApi.emailApi().sendEmail(payload);
	}

	@Test(expected = SendGridException.class)
	public void shouldThowsAnSendGridExceptionWhenResponseFamilyCodeIsClientError() throws Exception {
		EmailPayload payload = createEmailWithValidPayload();

		when(restClient.target(SENDGRID_API_SEND_EMAIL)).thenReturn(webTarget);
		when(webTarget.request(APPLICATION_JSON)).thenReturn(invocationBuilder);
		when(invocationBuilder.header("Authorization", "Bearer " + whateverKey.getApiKey())).thenReturn(invocationBuilder);
		when(invocationBuilder.post(json(payload))).thenReturn(response);
		when(response.getStatusInfo()).thenReturn(statusType);
		when(statusType.getFamily()).thenReturn(CLIENT_ERROR);

		clientApi.emailApi().sendEmail(payload);
	}

	@Test(expected = SendGridException.class)
	public void shouldThowsAnSendGridExceptionWhenResponseFamilyCodeIsServerError() throws Exception {
		EmailPayload payload = createEmailWithValidPayload();

		when(restClient.target(SENDGRID_API_SEND_EMAIL)).thenReturn(webTarget);
		when(webTarget.request(APPLICATION_JSON)).thenReturn(invocationBuilder);
		when(invocationBuilder.header("Authorization", "Bearer " + whateverKey.getApiKey())).thenReturn(invocationBuilder);
		when(invocationBuilder.post(json(payload))).thenReturn(response);
		when(response.getStatusInfo()).thenReturn(statusType);
		when(statusType.getFamily()).thenReturn(SERVER_ERROR);

		clientApi.emailApi().sendEmail(payload);
	}

	@Test
	public void shouldCreateAValidJsonPayload() throws Exception {
		EmailPayload payload = createEmailWithValidPayload();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		StringWriter out = new StringWriter();
		objectMapper.writeValue(out, payload);

		System.out.println(out);
	}

	private EmailPayload createEmailWithValidPayload() {
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

}
