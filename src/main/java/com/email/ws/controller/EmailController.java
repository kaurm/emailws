package com.email.ws.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.email.ws.beans.EmailBean;
import com.email.ws.service.EmailService;

/**
 * Controller to send emails.
 * 
 * @author mandeepk
 *
 */
@RestController
@EnableRetry
@EnableConfigurationProperties
public class EmailController {

	@Autowired
	@Qualifier(value = "mailGunEmailService")
	public EmailService emailService;

	
	@RequestMapping(method = RequestMethod.GET, value = "/check")
	@ResponseBody
	public String check() throws Exception {
		return "Service is running...";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/sendEmail")
	@ResponseBody
	public String sendEmail(@Valid @RequestBody EmailBean email) throws Exception {
		emailService.sendEmail(email);
		return "Email Sent Successfully!!";
	}

}
