package com.email.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.email.ws")
public class EmailWsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EmailWsApplication.class, args);
	}
}
