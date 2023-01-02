package com.codestates.sof.domain.auth.service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codestates.sof.domain.auth.entity.VerificationToken;
import com.codestates.sof.domain.auth.jwt.JwtTokenizer;
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
	private final RedisTemplate<String, String> redisTemplate;
	private final JwtTokenizer jwtTokenizer;

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

	@Transactional
	public void logout(HttpServletRequest request) {
		// 요청한 access token의 유효성 확인
		String accessToken = request.getHeader("Authorization");
		if (accessToken == null || !accessToken.startsWith("Bearer ")) {
			throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN);
		}

		String jws = accessToken.replace("Bearer ", "");
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
		Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

		// Redis에서 해당 username로 저장된 refresh token이 있는지 여부를 확인 후, 있을 경우 삭제
		if (redisTemplate.opsForValue().get("RTK:" + claims.get("username")) != null) {
			redisTemplate.delete("RTK:" + claims.get("username"));
		}

		// access token의 남은 시간을 redis에 남은 시간을 만료시간으로 하는 blacklist 데이터로 저장힙니다.
		long expiration = (Long)claims.get("expiration") - new Date().getTime();
		redisTemplate.opsForValue().set(jws, "blacklist", expiration, TimeUnit.MILLISECONDS);

		SecurityContextHolder.clearContext();
	}
}