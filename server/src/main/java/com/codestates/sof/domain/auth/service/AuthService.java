package com.codestates.sof.domain.auth.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codestates.sof.domain.auth.entity.VerificationToken;
import com.codestates.sof.domain.auth.repository.VerificationTokenRepository;
import com.codestates.sof.domain.common.RandomPasswordGenerator;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
	private final VerificationTokenRepository verificationTokenRepository;
	private final MemberRepository memberRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;

	public String generateToken(Member member, int expirationHour, VerificationToken.tokenType tokenType) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setMember(member);
		verificationToken.setExpiryDate(expirationHour);
		verificationToken.setTokenType(tokenType);

		verificationTokenRepository.save(verificationToken);

		return token;
	}

	public ResponseEntity<?> verifyAccount(String token) {
		Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);
		VerificationToken verificationToken = optionalToken.orElseThrow(() -> {
			throw new BusinessLogicException(ExceptionCode.NOT_FOUND_TOKEN);
		});

		// token type이 맞는지 확인합니다.
		if (verificationToken.getTokenType() != VerificationToken.tokenType.VERIFICATION) {
			throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN);
		}

		Member member = findMemberByTokenEmail(verificationToken);

		// 이메일 인증 토큰이 만료되면 다시 메일을 보내 재인증을 요청한다.
		if (verificationToken.isExpired()) {
			String newToken = generateToken(member, 60, VerificationToken.tokenType.VERIFICATION);
			emailService.sendActivationEmail(member, newToken);
			return new ResponseEntity<>("Token is expired. Check your email again.", HttpStatus.SERVICE_UNAVAILABLE);
		}

		member.setVerificationFlag(true);
		memberRepository.save(member);

		return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
	}

	public void resetPassword(String token) {
		Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);
		VerificationToken verificationToken = optionalToken.orElseThrow(() -> {
			throw new BusinessLogicException(ExceptionCode.NOT_FOUND_TOKEN);
		});

		// token type이 맞는지 확인합니다.
		if (verificationToken.getTokenType() != VerificationToken.tokenType.PASSWORD_RESET) {
			throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN);
		}

		Member member = findMemberByTokenEmail(verificationToken);
		String generatePassword = RandomPasswordGenerator.generate(2, 2, 2, 2);

		member.setBeforeEncryptedPassword(member.getEncryptedPassword());
		member.setEncryptedPassword(passwordEncoder.encode(generatePassword));
		memberRepository.save(member);

		emailService.sendPasswordResetEmail(member, generatePassword);
	}

	private Member findMemberByTokenEmail(VerificationToken verificationToken) {
		String memberEmail = verificationToken.getMember().getEmail();
		Optional<Member> optionalMember = memberRepository.findByEmail(memberEmail);

		Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_MEMBER));
		if (member.getDeleteFlag()) {
			throw new BusinessLogicException(ExceptionCode.NOT_FOUND_MEMBER);
		}
		return member;
	}
}