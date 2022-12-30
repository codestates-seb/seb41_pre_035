package com.codestates.sof.domain.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.global.email.EmailSender;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {
	@Value("${server.domain}")
	private String DOMAIN;

	@Value("${server.port}")
	private String PORT;

	private final EmailSender emailSender;

	public EmailService(EmailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void sendActivationEmail(Member member, String token) {
		try {
			String[] to = new String[] {member.getEmail()};
			int expiration = 60;
			String subject = "[seb41-pre-035] Thank you for joining Stackoverflow clone!";
			Map<String, Object> variables = new HashMap<>();
			variables.put("subject", subject);
			variables.put("message",
				"Thank you for signing up to Stack overflow clone site, please click on the below to activate your account.");
			variables.put("accountVerificationLink", DOMAIN + ":" + PORT + "/auth/accountVerification/" + token);
			variables.put("expiration", expiration);
			String templateName = "email-verification-template";
			emailSender.sendEmail(to, subject, variables, templateName);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MailSendException: rollback for Member Registration:");
			throw new BusinessLogicException(ExceptionCode.EMAIL_SEND_FAILURE);
		}
	}

	public void sendPasswordResetVerifyEmail(Member member, String token) {
		try {
			String[] to = new String[] {member.getEmail()};
			int expiration = 60;
			String subject = "[seb41-pre-035] Stackoverflow clone Reset Password";
			Map<String, Object> variables = new HashMap<>();
			variables.put("subject", subject);
			variables.put("message",
				String.format(
					"Reset the password for the %s email at user request. Please click on the below to verify your account.",
					member.getEmail()));
			variables.put("accountVerificationLink", DOMAIN + ":" + PORT + "/auth/password/" + token);
			variables.put("expiration", expiration);

			String templateName = "email-verification-template";
			emailSender.sendEmail(to, subject, variables, templateName);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MailSendException: rollback for Member Registration:");
			throw new BusinessLogicException(ExceptionCode.EMAIL_SEND_FAILURE);
		}
	}

	public void sendPasswordResetEmail(Member member, String password) {
		String[] to = new String[] {member.getEmail()};
		String subject = "[seb41-pre-035] Stackoverflow clone Reset Password";
		Map<String, Object> variables = new HashMap<>();
		variables.put("message",
			"Your account password has been successfully reset. The initialized passwords are as follows:");
		variables.put("email", member.getEmail());
		variables.put("password", password);
		String templateName = "password-reset-email";

		try {
			emailSender.sendEmail(to, subject, variables, templateName);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MailSendException: rollback for Member Registration:");
			throw new BusinessLogicException(ExceptionCode.EMAIL_SEND_FAILURE);
		}
	}
}
