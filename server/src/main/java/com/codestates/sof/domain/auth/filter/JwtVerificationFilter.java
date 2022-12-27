package com.codestates.sof.domain.auth.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codestates.sof.domain.auth.jwt.JwtTokenizer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
	private final JwtTokenizer jwtTokenizer;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		Map<String, Object> claims = verifyJws(request);

		setAuthenticationToContext(claims);

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");

		return authorization == null || !authorization.startsWith("Bearer");
	}

	private void setAuthenticationToContext(Map<String, Object> claims) {
		String username = (String)claims.get("username");
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private Map<String, Object> verifyJws(HttpServletRequest request) {
		String jws = request.getHeader("Authorization").replace("Bearer ", "");
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
		Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

		return claims;
	}
}