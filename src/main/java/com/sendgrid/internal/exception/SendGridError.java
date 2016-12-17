package com.sendgrid.internal.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	@Override
	public String toString() {
		return "SendGridError [message=" + message + ", field=" + field + ", help=" + help + "]";
	}

}
