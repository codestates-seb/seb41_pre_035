package com.codestates.sof.global.email;

import java.util.Map;

import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public interface EmailSendable {
	void send(String[] to, String subject, Map<String, Object> variables, String templateName)
		throws InterruptedException, MessagingException;
}