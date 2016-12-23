package com.sendgrid.api.client;

import com.sendgrid.api.email.EmailPayload;

public interface EmailApi {

	void sendEmail(EmailPayload payload);

}
