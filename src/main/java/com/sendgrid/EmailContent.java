package com.sendgrid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(Include.NON_NULL)
public class EmailContent {

	@JsonProperty(value = "type")
	private EmailMimeType type;
	
	@JsonProperty(value = "value")
	private String value;
	
	public enum EmailMimeType {
		TEXT_PLAIN("text/plain"), TEXT_HTML("text/html");
		
		private String mimeType;
		
		EmailMimeType(String mimeType) {
			this.mimeType = mimeType;
		}

		@JsonValue
		public String getMimeType() {
			return mimeType;
		}
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EmailMimeType getType() {
		return type;
	}

	public void setType(EmailMimeType type) {
		this.type = type;
	}
	
}
