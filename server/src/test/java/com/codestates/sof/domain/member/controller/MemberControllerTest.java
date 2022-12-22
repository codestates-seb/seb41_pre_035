package com.codestates.sof.domain.member.controller;

import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.sof.domain.member.dto.MemberDto;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.mapper.MemberMapper;
import com.codestates.sof.domain.member.service.MemberService;
import com.google.gson.Gson;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class MemberControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberService memberService;

	@MockBean
	private MemberMapper mapper;

	@Autowired
	private Gson gson;

	@Test
	public void postMemberTest() throws Exception {
		// given
		MemberDto.Post post = new MemberDto.Post();
		post.setEmail("user@hello.com");
		post.setPassword("1111");
		post.setName("user");

		long memberId = 1L;
		MemberDto.Response response = new MemberDto.Response(
			memberId,
			"user@hello.com",
			"user",
			false,
			LocalDateTime.now());

		String content = gson.toJson(post);

		given(mapper.memberPostDtoToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());
		given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());
		given(mapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(
			post("/members")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));

		// then
		// TODO : 요청값 필수여부, 제한사항 옵션 적용
		// TODO : SingleResponseDto wrapping
		actions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.memberId").value(memberId))
			.andExpect(jsonPath("$.data.email").value(post.getEmail()))
			.andExpect(jsonPath("$.data.name").value(post.getName()))
			.andExpect(jsonPath("$.data.verificationFlag").value(false))
			.andExpect(jsonPath("$.data.createdAt").exists())
			.andDo(document("post-member",
				getRequestPreProcessor(),
				getResponsePreProcessor(),
				requestFields(
					List.of(
						fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
						fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
					)
				),
				responseFields(
					List.of(
						fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
						fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("고유 식별자"),
						fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("data.verificationFlag").type(JsonFieldType.BOOLEAN).description("이메일 인증여부"),
						fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성일자")
					)
				)));
	}
}