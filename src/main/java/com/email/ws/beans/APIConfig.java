package com.email.ws.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties
public class APIConfig {

	@Value("${emailws.sendgrid.apiKey}")
	private String apiKey;
	
	@Value("${emailws.sendgrid.endpoint}")
	private String apiEndpoint;
	
	@Value("${emailws.from.email}")
	private String fromEmail;
	
	@Value("${emailws.sendgrid.body.type}")
	private String emailBodyContentType;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiEndpoint() {
		return apiEndpoint;
	}

	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getEmailBodyContentType() {
		return emailBodyContentType;
	}

	public void setEmailBodyContentType(String emailBodyContentType) {
		this.emailBodyContentType = emailBodyContentType;
	}

}
