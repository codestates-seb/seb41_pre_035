package com.codestates.sof.domain.auth.jwt;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.Decoders;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtTokenizerTest {
	private static JwtTokenizer jwtTokenizer;
	private String secretKey;
	private String base64EncodedSecretKey;

	@BeforeAll
	public void init() {
		jwtTokenizer = new JwtTokenizer();
		secretKey = "ThisIsJwtTokenizerTestSecretKeyThisIsJwtTokenizerTestSecretKey";
		base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);
	}

	@Test
	@DisplayName("Secret key를 올바르게 encode, decode 해야합니다.")
	public void encodeBase64SecretKeyTest() {
		assertThat(secretKey, is(new String(Decoders.BASE64.decode(base64EncodedSecretKey))));
	}

	@Test
	@DisplayName("Access token를 에러 없이 정상적으로 생성하여야 합니다.")
	public void generateAccessTokenTest() {
		Map<String, Object> claims = new HashMap<>();
		claims.put("memberId", 1);
		claims.put("roles", List.of("USER"));

		String subject = "Test access token";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 5);
		Date expiration = calendar.getTime();

		String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

		assertThat(accessToken, notNullValue());
	}

	@Test
	@DisplayName("Refresh token를 에러 없이 정상적으로 생성하여야 합니다.")
	public void generateRefreshTokenTest() {
		String subject = "Test refresh token";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 24);
		Date expiration = calendar.getTime();
		String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

		assertThat(refreshToken, notNullValue());
	}

	@Test
	@DisplayName("만들어진 JWT를 에러없이 정상적으로 검증해야 합니다.")
	public void verifySignatureTest() {
		String accessToken = getAccessToken(Calendar.MINUTE, 5);
		assertDoesNotThrow(() -> jwtTokenizer.verifySignature(accessToken, base64EncodedSecretKey));
	}

	@Test
	@DisplayName("JWT가 만료되면 검증에 실패해야 합니다.")
	public void verifyExpirationTest() throws InterruptedException {
		String accessToken = getAccessToken(Calendar.SECOND, 1);
		assertDoesNotThrow(() -> jwtTokenizer.verifySignature(accessToken, base64EncodedSecretKey));

		TimeUnit.MILLISECONDS.sleep(2000);
		assertThrows(ExpiredJwtException.class, () -> jwtTokenizer.verifySignature(accessToken, base64EncodedSecretKey));
	}

	private String getAccessToken(int time, int i) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("memberId", 1);
		claims.put("roles", List.of("USER"));

		String subject = "Test access token";
		Calendar calendar = Calendar.getInstance();
		calendar.add(time, i);
		Date expiration = calendar.getTime();

		return jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
	}
}