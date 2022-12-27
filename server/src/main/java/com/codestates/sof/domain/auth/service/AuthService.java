package com.codestates.sof.domain.auth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codestates.sof.domain.auth.entity.VerificationToken;
import com.codestates.sof.domain.auth.repository.VerificationTokenRepository;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.global.email.EmailSender;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
	@Value("${server.domain}")
	private String DOMAIN;

	@Value("${server.port}")
	private String PORT;

	private final VerificationTokenRepository verificationTokenRepository;
	private final MemberRepository memberRepository;
	private final EmailSender emailSender;

	public AuthService(VerificationTokenRepository verificationTokenRepository, MemberRepository memberRepository,
		EmailSender emailSender) {
		this.verificationTokenRepository = verificationTokenRepository;
		this.memberRepository = memberRepository;
		this.emailSender = emailSender;
	}

	public String generateVerificationToken(Member user, int expirationHour) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setMember(user);
		verificationToken.setExpiryDate(expirationHour);

		verificationTokenRepository.save(verificationToken);

		return token;
	}

	public ResponseEntity<?> verifyAccount(String token) {
		Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);
		VerificationToken verificationToken = optionalToken.orElseThrow(() -> {
			throw new BusinessLogicException(ExceptionCode.NOT_FOUND_TOKEN);
		});

		String memberEmail = verificationToken.getMember().getEmail();
		Member member = memberRepository.findByEmail(memberEmail).orElseThrow(() -> {
			throw new BusinessLogicException(ExceptionCode.NOT_FOUND_MEMBER);
		});

		// 이메일 인증 토큰이 만료되면 다시 메일을 보내 재인증을 요청한다.
		if (verificationToken.isExpired()) {
			sendActivationEmail(member);
			return new ResponseEntity<>("Token is expired. Check your email again.", HttpStatus.SERVICE_UNAVAILABLE);
		}

		member.setVerificationFlag(true);
		memberRepository.save(member);

		return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
	}

	public void sendActivationEmail(Member member) {
		try {
			String[] to = new String[] {member.getEmail()};
			int expiration = 60;
			String token = generateVerificationToken(member, expiration);
			String subject = "[seb41-pre-035] Thank you for joining Stackoverflow clone!";
			Map<String, Object> variables = new HashMap<>();
			variables.put("message",
				"Thank you for signing up to Stack overflow clone site, please click on the below to activate your account.");
			variables.put("accountVerificationLink", DOMAIN + ":" + PORT + "/auth/accountVerification/" + token);
			variables.put("expiration", expiration );
			String templateName = "sign-in-email-template";

			emailSender.sendEmail(to, subject, variables, templateName);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("MailSendException: rollback for Member Registration:");
			memberRepository.deleteById(member.getMemberId());
			throw new BusinessLogicException(ExceptionCode.EMAIL_SEND_FAILURE);
		}
	}
}