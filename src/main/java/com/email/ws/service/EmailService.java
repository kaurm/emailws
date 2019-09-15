package com.email.ws.service;

import java.io.IOException;

import com.email.ws.beans.EmailBean;

public interface EmailService {

	void sendEmail(EmailBean emailBean) throws Exception;
}
