package com.codestates.sof.domain.auth.controller;

import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.sof.domain.auth.dto.LoginDto;
import com.codestates.sof.domain.auth.service.AuthService;
import com.codestates.sof.domain.auth.service.EmailService;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.domain.member.service.MemberService;
import com.google.gson.Gson;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class AuthControllerRestDocsTest {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private Gson gson;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberService memberService;

	@MockBean
	private AuthService authService;

	@MockBean
	private EmailService emailService;

	@BeforeEach
	public void init() {
		// 로그인에 필요한 기존 사용자 추가
		Member member = new Member();
		member.setEmail("user01@hello.com");
		member.setName("user01");
		member.setEncryptedPassword(passwordEncoder.encode("1111"));
		member.setVerificationFlag(true);
		memberRepository.save(member);
	}

	@Test
	public void postLoginTest() throws Exception {
		// given
		LoginDto.Post post = new LoginDto.Post();
		post.setUsername("user01@hello.com");
		post.setPassword("1111");
		String content = gson.toJson(post);

		// when
		ResultActions actions = mockMvc.perform(
			post("/auth/login")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));

		// then
		actions.andExpect(status().isOk())
			.andExpect(header().exists("Authorization"))
			.andExpect(header().exists("Refresh"))
			.andDo(document("login",
				getRequestPreProcessor(),
				getResponsePreProcessor(),
				requestFields(
					List.of(
						fieldWithPath("username").type(JsonFieldType.STRING).description("로그인 id(이메일)"),
						fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
					)
				),
				responseHeaders(
					headerWithName("Authorization").description("액세스 토큰(Access token)"),
					headerWithName("Refresh").description("리프레시 토큰(Refresh token)")
				),
				responseFields(
					List.of(
						fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("고유 식별자"),
						fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("avatarUrl").type(JsonFieldType.STRING).description("프로필사진").optional()
					)
				)
			));
	}

	@Test
	public void forgetPasswordTest() throws Exception {
		// given
		String email = "user01@hello.com";
		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		String content = gson.toJson(map);

		given(memberService.findMemberByEmail(Mockito.anyString())).willReturn(new Member());
		given(authService.generateToken(Mockito.any(Member.class), Mockito.anyInt(), Mockito.any())).willReturn("");
		doNothing().when(emailService).sendPasswordResetVerifyEmail(Mockito.any(Member.class), Mockito.anyString());

		// when
		ResultActions actions = mockMvc.perform(
			get("/auth/password")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));

		// then
		actions.andExpect(status().isOk())
			.andDo(document("forget-password",
				getRequestPreProcessor(),
				getResponsePreProcessor(),
				requestFields(
					List.of(fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 id(이메일)"))
				)
			));
	}

	@Test
	public void logoutTest() throws Exception {
		// given
		doNothing().when(authService).logout(Mockito.any(HttpServletRequest.class));

		// when
		ResultActions actions = mockMvc.perform(
			post("/auth/logout"));

		// then
		actions.andExpect(status().isNoContent())
			.andDo(document("logout",
				getRequestPreProcessor(),
				getResponsePreProcessor()
			));
	}
}