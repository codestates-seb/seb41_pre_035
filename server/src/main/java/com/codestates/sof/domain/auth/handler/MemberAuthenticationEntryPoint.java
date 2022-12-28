package com.codestates.sof.domain.auth.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.codestates.sof.global.error.dto.ErrorResponse;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		Exception exception = (Exception)request.getAttribute("exception");
		String message = authException != null ? authException.getMessage() : exception.getMessage();

		Gson gson = new Gson();
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED, message);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));

		log.warn("Unauthorized error happened: {}", message);
	}
}