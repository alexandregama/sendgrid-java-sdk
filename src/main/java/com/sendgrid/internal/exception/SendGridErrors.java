package com.sendgrid.internal.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class SendGridErrors {

	@JsonProperty(value = "errors")
	private List<SendGridError> typedErrors;

	public List<SendGridError> getTypedErrors() {
		return typedErrors;
	}

	@Override
	public String toString() {
		return "[Typed Errors = " + typedErrors + "]";
	}

}
