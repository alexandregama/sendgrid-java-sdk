package com.sendgrid.internal.email;

import com.sendgrid.api.client.EmailApi;
import com.sendgrid.api.client.EmailClientApi;
import com.sendgrid.api.client.SendGridApiKey;

public class DefaultEmailClientApi implements EmailClientApi {

	private SendGridApiKey key;

	public DefaultEmailClientApi(SendGridApiKey key) {
		this.key = key;
	}
	
	@Override
	public EmailApi emailApi() {
		return new JerseyEmailApi(key);
	}

}
