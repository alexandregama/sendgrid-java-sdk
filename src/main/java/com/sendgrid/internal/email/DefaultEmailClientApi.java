package com.sendgrid.internal.email;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import com.sendgrid.api.client.EmailApi;
import com.sendgrid.api.client.EmailClientApi;
import com.sendgrid.api.client.SendGridApiKey;

public class DefaultEmailClientApi implements EmailClientApi {

	private SendGridApiKey key;

	private Client restClient;

	private DefaultEmailClientApi(SendGridApiKey key) {
		this.key = key;
	}

	private DefaultEmailClientApi(SendGridApiKey key, Client client) {
		this.key = key;
		this.restClient = client;
	}

	@Override
	public EmailApi emailApi() {
		if (restClient != null) {
			return new JerseyEmailApi(key, restClient);
		}
		JerseyClient defaultClient = new JerseyClientBuilder().build();
		return new JerseyEmailApi(key, defaultClient);
	}

	public static DefaultEmailClientApiWithKey withSecretKey(SendGridApiKey key) {
		return new DefaultEmailClientApiWithKey(key);
	}

	public static class DefaultEmailClientApiWithKey {

		private SendGridApiKey secretKey;

		public DefaultEmailClientApiWithKey(SendGridApiKey key) {
			this.secretKey = key;
		}

		public DefaultEmailClientApi getApiAccess() {
			return new DefaultEmailClientApi(secretKey);
		}

		public DefaultEmailClientApi overridingTheDefaultRestClientWith(Client anotherClient) {
			return new DefaultEmailClientApi(secretKey, anotherClient);
		}

	}

}
