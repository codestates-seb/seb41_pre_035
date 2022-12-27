package com.codestates.sof.global.email;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateEmailSendable implements EmailSendable {
	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;
	private final Context context;

	public TemplateEmailSendable(JavaMailSender javaMailSender, TemplateEngine templateEngine, Context context) {
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
		this.context = context;
	}

	@Override
	public void send(String[] to, String subject, Map<String, Object> variables, String templateName) {
		try {
			context.setVariables(variables);

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

			String html = templateEngine.process(templateName, context);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(html, true);

			javaMailSender.send(mimeMessage);
			for (String t : to) {
				log.info("# Send a email to : " + t);
			}
		} catch (MessagingException e) {
			log.error("email send error: ", e);
			throw new RuntimeException(e);
		}
	}
}
