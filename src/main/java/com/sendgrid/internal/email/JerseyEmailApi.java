package com.sendgrid.internal.email;

import static javax.ws.rs.client.Entity.json;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.Status.Family.CLIENT_ERROR;
import static javax.ws.rs.core.Response.Status.Family.SERVER_ERROR;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import com.sendgrid.api.client.EmailApi;
import com.sendgrid.api.client.SendGridApiKey;
import com.sendgrid.api.email.EmailPayload;
import com.sendgrid.internal.exception.SendGridErrors;
import com.sendgrid.internal.exception.SendGridException;

class JerseyEmailApi implements EmailApi {

	private static final String SENDGRID_API_SEND_EMAIL = "https://api.sendgrid.com/v3/mail/send";

	private static final String AUTHORIZATION = "Authorization";

	private final SendGridApiKey key;

	private Client client;

	public JerseyEmailApi(SendGridApiKey key, Client client) {
		this.key = key;
		this.client = client;
	}

	@Override
	public void sendEmail(EmailPayload payload) {
		Response response = client
			.target(SENDGRID_API_SEND_EMAIL)
			.request(APPLICATION_JSON)
			.header(AUTHORIZATION, "Bearer " + key.getApiKey())
			.post(json(payload));

		if (response.getStatusInfo().equals(UNAUTHORIZED)) {
			SendGridErrors errors = response.readEntity(SendGridErrors.class);
			throw new SendGridException("Unauthorized user to send an email by SendGrid", errors);
		}
		if (response.getStatusInfo().equals(BAD_REQUEST)) {
			SendGridErrors errors = response.readEntity(SendGridErrors.class);
			throw new SendGridException("Bad Request while trying to send an email by SendGrid", errors);
		}
		if (response.getStatusInfo().getFamily().equals(CLIENT_ERROR)) {
			SendGridErrors errors = response.readEntity(SendGridErrors.class);
			throw new SendGridException("An error ocurred while trying to send an email by SendGrid", errors);
		}
		if (response.getStatusInfo().getFamily().equals(SERVER_ERROR)) {
			SendGridErrors errors = response.readEntity(SendGridErrors.class);
			throw new SendGridException("An error ocurred in the server while trying to send an email by SendGrid", errors);
		}
	}

}
