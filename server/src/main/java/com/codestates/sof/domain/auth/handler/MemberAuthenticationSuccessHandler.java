package com.codestates.sof.domain.auth.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.codestates.sof.domain.auth.dto.LoginDto;
import com.codestates.sof.domain.auth.mapper.LoginMapper;
import com.codestates.sof.domain.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final LoginMapper mapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		// authentication에 있는 member 정보를 가지고와 로그인 하고 있는 사용자의 최소 정보를 응답값으로 반환합니다.
		Member member = (Member)authentication.getPrincipal();

		LoginDto.Response responseDto = mapper.loginMemberToLoginResponseDto(member);
		String body = new ObjectMapper().writeValueAsString(responseDto);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(body);

		log.info("# Member Authentication success");
	}
}