package com.sendgrid.internal.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class SendGridError {

	@JsonProperty(value = "message")
	private String message;

	@JsonProperty(value = "field")
	private String field;

	@JsonProperty(value = "help")
	private String help;

	public String getMessage() {
		return message;
	}

	public String getField() {
		return field;
	}

	public String getHelp() {
		return help;
	}

	@Override
	public String toString() {
		return "SendGridError [message=" + message + ", field=" + field + ", help=" + help + "]";
	}

}
