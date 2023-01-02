package com.codestates.sof.global.email;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import com.codestates.sof.global.email.EmailSendable;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailSender {
	private final EmailSendable emailSendable;

	public void sendEmail(String[] to, String subject, Map<String, Object> variables, String templateName)
		throws MailSendException, InterruptedException, MessagingException {
		emailSendable.send(to, subject, variables, templateName);
	}
}