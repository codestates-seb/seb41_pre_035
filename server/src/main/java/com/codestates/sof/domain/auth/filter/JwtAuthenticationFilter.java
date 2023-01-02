package com.codestates.sof.domain.auth.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.codestates.sof.domain.auth.dto.LoginDto;
import com.codestates.sof.domain.auth.jwt.JwtTokenizer;
import com.codestates.sof.domain.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	private final JwtTokenizer jwtTokenizer;
	private final RedisTemplate<String, String> redisTemplate;

	@SneakyThrows
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper om = new ObjectMapper();
		LoginDto.Post loginDto = om.readValue(request.getInputStream(), LoginDto.Post.class);

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

		// TODO : Custom AuthenticationProvider 구현해서 다양한 로그인 인증을 시도하기
		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain, Authentication authentication) throws ServletException, IOException {
		Member member = (Member)authentication.getPrincipal();

		String accessToken = delegateAccessToken(member);

		Map<String, Object> map = delegateRefreshToken(member);
		String refreshToken = (String)map.get("refresh");

		redisTemplate.opsForValue()
			.set("RTK:" + authentication.getName(), refreshToken, ((Date)map.get("expiration")).getTime(),
				TimeUnit.MINUTES);

		response.setHeader("Authorization", "Bearer " + accessToken);
		response.setHeader("Refresh", refreshToken);

		this.getSuccessHandler().onAuthenticationSuccess(request, response, authentication);
	}

	private String delegateAccessToken(Member member) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", member.getEmail());

		String subject = member.getEmail();
		Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		claims.put("expiration", expiration);

		String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

		return accessToken;
	}

	private Map<String, Object> delegateRefreshToken(Member member) {
		String subject = member.getEmail();
		Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

		String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

		Map<String, Object> map = new HashMap<>();
		map.put("refresh", refreshToken);
		map.put("expiration", expiration);

		return map;
	}
}