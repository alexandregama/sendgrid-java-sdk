package com.sendgrid.api.email;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class EmailInformation {

	@JsonProperty(value = "email")
	@NotNull
	private String email;

	@JsonProperty(value = "name")
	private String name;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static EmailBuilder newEmail() {
		return new EmailBuilder();
	}

	public static class EmailBuilder {

		private EmailInformation email;

		public EmailBuilder() {
			this.email = new EmailInformation();
		}

		public EmailBuilder withNameTo(String name) {
			this.email.setName(name);
			return this;
		}

		public EmailBuilder withEmailTo(String emailTo) {
			this.email.setEmail(emailTo);
			return this;
		}

		public EmailInformation build() {
			return this.email;
		}

	}

}
