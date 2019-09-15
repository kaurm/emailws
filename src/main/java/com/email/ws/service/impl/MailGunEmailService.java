package com.email.ws.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.email.ws.beans.EmailBean;
import com.email.ws.service.EmailService;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

@Service
public class MailGunEmailService implements EmailService {
	private static final Logger LOG = Logger.getLogger(MailGunEmailService.class);

	@Value("${emailws.mailgun.apiKey}")
	private String mailgunApiKey;

	@Value("${emailws.mailgun.host}")
	private String mailgunHost;

	@Value("${emailws.mailgun.endpoint}")
	private String mailgunEndpoint;

	@Value("${emailws.from.email}")
	private String fromEmail;

	@Autowired
	@Qualifier(value = "sendGridEmailService")
	private EmailService sendGridEmailService;

	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	public void sendEmail(EmailBean emailBean) throws Exception {
		LOG.info("Sending email from Mailgun..");
		try {
			retryableEmail(emailBean);
		} catch (UnirestException e) {
			LOG.error("Error sending emil via Mialgun", e);
			throw new Exception(e.getMessage());
		}

	}

	private void retryableEmail(EmailBean emailBean) throws UnirestException {
		HttpRequestWithBody requestBody = Unirest.post(mailgunHost + mailgunEndpoint).basicAuth("api", mailgunApiKey);
		requestBody.queryString("from", fromEmail).queryString("to", emailBean.getTo())
				.queryString("subject", emailBean.getSubject()).queryString("text", emailBean.getBody());

		if (!CollectionUtils.isEmpty(emailBean.getCc())) {
			requestBody.queryString("cc", emailBean.getCc());
		}

		if (!CollectionUtils.isEmpty(emailBean.getBcc())) {
			requestBody.queryString("bcc", emailBean.getBcc());
		}

	}

	@Recover
	public void recover(Exception t, EmailBean emailBean) throws Exception {
		LOG.info("Mailgun failed to recover, switching to Sendgrid.");
		sendGridEmailService.sendEmail(emailBean);

	}

}
