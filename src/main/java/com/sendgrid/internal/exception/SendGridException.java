package com.sendgrid.internal.exception;

public class SendGridException extends RuntimeException {

	private static final long serialVersionUID = 5571566826079230340L;
	
	private String message;

	private SendGridErrors errors;

	public SendGridException(String message, SendGridErrors errors) {
		this.message = message;
		this.errors = errors;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public SendGridErrors getErrors() {
		return errors;
	}

}
