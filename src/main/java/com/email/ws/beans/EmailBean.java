package com.email.ws.beans;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Input bean for email details
 * 
 * @author mandeepk
 *
 */
public class EmailBean {

	@NotNull
	private List<@Email(message = "Email provided in TO is not valid.") String> to;

	private List<@Email(message = "Email provided in CC is not valid.") String> cc;

	private List<@Email(message = "Email provided in BCC is not valid.") String> bcc;

	@NotEmpty(message = "Email Subject can not be empty.")
	private String subject;

	@NotEmpty(message = "Email body can not be empty.")
	private String body;

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public List<String> getBcc() {
		return bcc;
	}

	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
