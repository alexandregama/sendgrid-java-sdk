package com.sendgrid.internal.email;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sendgrid.api.client.EmailApi;
import com.sendgrid.api.client.SendGridApiKey;
import com.sendgrid.api.email.EmailPayload;
import com.sendgrid.internal.exception.SendGridErrors;
import com.sendgrid.internal.exception.SendGridException;

class JerseyEmailApi implements EmailApi {

	private static final String SENDGRID_API_SEND_EMAIL = "https://api.sendgrid.com/v3/mail/send";
	private static final String AUTHORIZATION = "Authorization";
	private final SendGridApiKey key;

	public JerseyEmailApi(SendGridApiKey key) {
		this.key = key;
	}

	@Override
	public void sendEmail(EmailPayload payload) {
		Response response = ClientBuilder
			.newClient()
			.target(SENDGRID_API_SEND_EMAIL)
			.request(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION, "Bearer " + key.getApiKey())
			.post(Entity.json(payload));

		if (response.getStatusInfo().equals(Status.UNAUTHORIZED)) {
			SendGridErrors errors = response.readEntity(SendGridErrors.class);
			throw new SendGridException("Unauthorized user to send an email by SendGrid", errors);
		}
		if (response.getStatusInfo().equals(Status.BAD_REQUEST)) {
			SendGridErrors errors = response.readEntity(SendGridErrors.class);
			throw new SendGridException("Bad Request while trying to send an email by SendGrid", errors);
		}
	}

}
