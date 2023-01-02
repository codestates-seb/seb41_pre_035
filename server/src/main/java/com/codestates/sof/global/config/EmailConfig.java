package com.codestates.sof.global.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.global.email.EmailSendable;
import com.codestates.sof.global.email.MockEmailSendable;
import com.codestates.sof.global.email.TemplateEmailSendable;

@Configuration
public class EmailConfig {
	@Value("${mail.smtp.host}")
	private String host;

	@Value("${mail.smtp.port}")
	private int port;

	@Value("${EMAIL_USERNAME}")
	private String username;

	@Value("${EMAIL_PASSWORD}")
	private String password;

	@Value("${mail.smtp.auth}")
	private String auth;

	@Value("${mail.smtp.starttls.enable}")
	private String tlsEnable;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		Properties properties = mailSender.getJavaMailProperties();
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.starttls.enable", tlsEnable);

		return mailSender;
	}

	@Bean
	@Profile("local")
	public EmailSendable testEmailSendable(MemberRepository memberRepository) {
		return new MockEmailSendable(memberRepository);
	}

	@Bean
	@Profile("server")
	public EmailSendable TemplateEmailSendable(TemplateEngine templateEngine) {
		return new TemplateEmailSendable(javaMailSender(), templateEngine, new Context());
	}
}