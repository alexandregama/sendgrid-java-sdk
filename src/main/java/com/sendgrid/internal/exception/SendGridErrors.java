package com.sendgrid.internal.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SendGridErrors {

	@JsonProperty(value = "errors")
	private List<SendGridError> errors;

	public List<SendGridError> getErrors() {
		return errors;
	}

	public void setErrors(List<SendGridError> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "SendGridErrors [errors=" + errors + "]";
	}
	
}
