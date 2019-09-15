package com.email.ws.service.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.email.ws.beans.APIConfig;
import com.email.ws.beans.EmailBean;
import com.email.ws.service.EmailService;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class SendGridEmailService implements EmailService {
	private static final Logger LOG = Logger.getLogger(SendGridEmailService.class);

	@Autowired
	@Qualifier(value = "mailGunEmailService")
	private EmailService mailGunService;

	@Autowired
	private APIConfig apiConfig;

	@Override
	@Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	public void sendEmail(EmailBean emailBean) throws Exception {
		LOG.info("Sending email from SendGrid..");
		try {
			retryableEmail(emailBean);
		} catch (IOException e) {
			LOG.error("Error sending emil via Sendgrid", e);
			throw new Exception(e.getMessage());
		}

	}

	private void retryableEmail(EmailBean emailBean) throws IOException {
		SendGrid sg = new SendGrid(apiConfig.getApiKey());
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint(apiConfig.getApiEndpoint());
			request.setBody(getEmailBody(emailBean));
			Response response = sg.api(request);
			LOG.info("Email sent via SendGrid, return status code: =" + response.getStatusCode());
		} catch (IOException ex) {
			LOG.error("Error sendign email via SendGrid: " + ex.getMessage());
			throw new IOException(ex.getMessage());
		}
	}

	@Recover
	public void recover(Exception t, EmailBean emailBean) throws Exception {
		LOG.info("SendGrid failed to recover, switching to Mailgun.");
		mailGunService.sendEmail(emailBean);

	}

	private String getEmailBody(EmailBean emailBean) throws IOException {
		Mail mail = new Mail();
		mail.setFrom(new Email(apiConfig.getFromEmail()));
		mail.setSubject(emailBean.getSubject());
		mail.addContent(new Content(apiConfig.getEmailBodyContentType(), emailBean.getBody()));

		Personalization personalization = new Personalization();
		if (!CollectionUtils.isEmpty(emailBean.getTo())) {
			emailBean.getTo().forEach(toEmail -> personalization.addTo(new Email(toEmail)));
		}
		if (!CollectionUtils.isEmpty(emailBean.getCc())) {
			emailBean.getCc().forEach(ccEmail -> personalization.addCc(new Email(ccEmail)));
		}

		if (!CollectionUtils.isEmpty(emailBean.getBcc())) {
			emailBean.getBcc().forEach(bccEmail -> personalization.addBcc(new Email(bccEmail)));
		}

		mail.addPersonalization(personalization);

		return mail.build();
	}

}
