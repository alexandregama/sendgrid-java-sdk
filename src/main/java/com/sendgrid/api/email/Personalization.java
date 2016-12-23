package com.sendgrid.api.email;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Personalization {

	@JsonProperty(value = "to", required = true)
	@NotNull
	private List<EmailInformation> emailsTo;

	@JsonProperty(value = "cc" , required = false)
	private List<EmailInformation> carbonCopyEmails;

	public void addEmailTo(EmailInformation email) {
		if (emailsTo == null) {
			emailsTo = new ArrayList<>();
		}
		emailsTo.add(email);
	}

	public List<EmailInformation> getEmailsTo() {
		return unmodifiableList(emailsTo);
	}

	public void addCc(EmailInformation email) {
		if (carbonCopyEmails == null) {
			carbonCopyEmails = new ArrayList<>();
		}
		carbonCopyEmails.add(email);
	}

	public List<EmailInformation> getCarbonCopyEmails() {
		return carbonCopyEmails;
	}

}
