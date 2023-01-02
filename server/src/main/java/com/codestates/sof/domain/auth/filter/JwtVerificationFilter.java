package com.codestates.sof.domain.auth.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codestates.sof.domain.auth.dto.MemberDetails;
import com.codestates.sof.domain.auth.jwt.JwtTokenizer;
import com.codestates.sof.domain.auth.service.MemberDetailsService;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
	private final MemberDetailsService detailsService;
	private final JwtTokenizer jwtTokenizer;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			Map<String, Object> claims = verifyJws(request);
			verifyBlacklist(request);
			setAuthenticationToContext(claims, request);
		} catch (SignatureException se) {
			request.setAttribute("exception", se);
		} catch (ExpiredJwtException ee) {
			request.setAttribute("exception", ee);
		} catch (Exception e) {
			request.setAttribute("exception", e);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");

		return authorization == null || !authorization.startsWith("Bearer");
	}

	private void setAuthenticationToContext(Map<String, Object> claims, HttpServletRequest request) {
		String username = (String)claims.get("username");

		MemberDetails userDetails = (MemberDetails)detailsService.loadUserByUsername(username);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
			null);
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private Map<String, Object> verifyJws(HttpServletRequest request) {
		String jws = resolveToken(request);
		String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
		Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

		return claims;
	}

	private void verifyBlacklist(HttpServletRequest request) {
		String jws = resolveToken(request);

		// jws가 블랙리스트라면 로그아웃된 상태이므로 필터를 통과하지 못합니다.
		if (StringUtils.hasText(jws) && !ObjectUtils.isEmpty(redisTemplate.opsForValue().get(jws))) {
			throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN);
		}
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			System.out.println(bearerToken);
			return bearerToken.substring(7);
		}
		return null;
	}
}