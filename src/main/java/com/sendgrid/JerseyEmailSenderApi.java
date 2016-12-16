package com.sendgrid;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

class JerseyEmailSenderApi implements EmailSenderApi {

	private static final String SENDGRID_API_SEND_EMAIL = "https://api.sendgrid.com/v3/mail/send";
	private static final String AUTHORIZATION = "Authorization";
	private final SendGridApiKey key;
	
	public JerseyEmailSenderApi(SendGridApiKey key) {
		this.key = key;
	}

	public void sendEmail(EmailPayload payload) {
		Response response = ClientBuilder
			.newClient()
			.target(SENDGRID_API_SEND_EMAIL)
			.request(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION, "Bearer " + key.getApiKey())
			.post(Entity.json(payload));
		
		System.out.println(response);
	}
	
}
