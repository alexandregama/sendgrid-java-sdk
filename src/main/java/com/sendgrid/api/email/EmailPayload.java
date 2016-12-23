package com.sendgrid.api.email;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class EmailPayload {

	@JsonProperty(value = "personalizations")
	private List<Personalization> personalizations = new ArrayList<>();
	
	@JsonProperty(value = "from", required = true)
	private EmailInformation emailFrom;
	
	@JsonProperty(value = "subject")
	private String subject;
	
	@JsonProperty(value = "content")
	private List<EmailContent> contents = new ArrayList<>();
	
	public void addPersonalization(Personalization personalization) {
		personalizations.add(personalization);
	}
	
	public List<Personalization> getPersonalizations() {
		return unmodifiableList(personalizations);
	}

	public EmailInformation getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(EmailInformation emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void addContent(EmailContent content) {
		contents.add(content);
	}
}
