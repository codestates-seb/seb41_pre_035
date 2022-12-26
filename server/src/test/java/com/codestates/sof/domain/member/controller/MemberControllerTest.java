package com.codestates.sof.domain.member.controller;

import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.codestates.sof.domain.member.dto.MemberDto;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.mapper.MemberMapper;
import com.codestates.sof.domain.member.service.MemberService;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

// TODO: 공통기능 메서드화
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
		MemberDto.Post post = (MemberDto.Post)StubData.MockMember.getRequestBody(HttpMethod.POST);
		MemberDto.Response response = StubData.MockMember.getSingleResponseBody();
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
		actions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.memberId").value(1L))
			.andExpect(jsonPath("$.data.email").value(post.getEmail()))
			.andExpect(jsonPath("$.data.name").value(post.getName()))
			.andExpect(jsonPath("$.data.verificationFlag").value(false))
			.andExpect(jsonPath("$.data.deleteFlag").value(false))
			.andExpect(jsonPath("$.data.lastActivateAt").exists())
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
						fieldWithPath("data.deleteFlag").type(JsonFieldType.BOOLEAN).description("탈퇴여부"),
						fieldWithPath("data.lastActivateAt").type(JsonFieldType.STRING).description("마지막 활동일자"),
						fieldWithPath("data.profile").type(JsonFieldType.OBJECT).description("추가 정보"),
						fieldWithPath("data.profile.title").type(JsonFieldType.STRING).description("자기소개 제목"),
						fieldWithPath("data.profile.aboutMe").type(JsonFieldType.STRING).description("자기소개 내용"),
						fieldWithPath("data.profile.location").type(JsonFieldType.STRING).description("지역"),
						fieldWithPath("data.profile.websiteLink").type(JsonFieldType.STRING).description("홈페이지 링크"),
						fieldWithPath("data.profile.twitterLink").type(JsonFieldType.STRING).description("트위터 링크"),
						fieldWithPath("data.profile.githubLink").type(JsonFieldType.STRING).description("깃헙 링크")
					)
				)));
	}

	@Test
	void getMembersTest() throws Exception {
		// given
		String page = "1";
		String size = "15";

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("page", page);
		queryParams.add("size", size);

		Page<Member> memberPage = StubData.MockMember.getMemberPage();
		List<MemberDto.Response> responses = StubData.MockMember.getMultiResponseBody();

		given(memberService.findAllMember(Mockito.anyInt(), Mockito.anyInt())).willReturn(memberPage);
		given(mapper.memberToMemberResponseDto(Mockito.anyList())).willReturn(responses);

		// when
		ResultActions actions = mockMvc.perform(
			get("/members")
				.params(queryParams)
				.accept(MediaType.APPLICATION_JSON));

		// then
		MvcResult result = actions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andDo(document("get-members",
				getRequestPreProcessor(),
				getResponsePreProcessor(),
				requestParameters(
					List.of(
						parameterWithName("page").description("Page 번호"),
						parameterWithName("size").description("Page 사이즈")
					)
				),
				responseFields(
					List.of(
						fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("고유 식별자"),
						fieldWithPath("data[].email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("data[].verificationFlag").type(JsonFieldType.BOOLEAN).description("이메일 인증여부"),
						fieldWithPath("data[].deleteFlag").type(JsonFieldType.BOOLEAN).description("탈퇴여부"),
						fieldWithPath("data[].lastActivateAt").type(JsonFieldType.STRING).description("마지막 활동일자"),
						fieldWithPath("data[].profile").type(JsonFieldType.OBJECT).description("추가 정보"),
						fieldWithPath("data[].profile.title").type(JsonFieldType.STRING).description("자기소개 제목"),
						fieldWithPath("data[].profile.aboutMe").type(JsonFieldType.STRING).description("자기소개 내용"),
						fieldWithPath("data[].profile.location").type(JsonFieldType.STRING).description("지역"),
						fieldWithPath("data[].profile.websiteLink").type(JsonFieldType.STRING).description("홈페이지 링크"),
						fieldWithPath("data[].profile.twitterLink").type(JsonFieldType.STRING).description("트위터 링크"),
						fieldWithPath("data[].profile.githubLink").type(JsonFieldType.STRING).description("깃헙 링크"),

						fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("Page 정보"),
						fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("Page 번호"),
						fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("Page 사이즈"),
						fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 멤버 수"),
						fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 Page 수")
					)
				)
			))
			.andReturn();

		List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
		assertThat(list.size(), is(responses.size()));
	}

	@Test
	void getMemberTest() throws Exception {
		// given
		long memberId = 1L;
		MemberDto.Response response = StubData.MockMember.getSingleResponseBody();

		given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
		given(mapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(response);

		// when
		ResultActions actions = mockMvc.perform(
			get("/members/{member-id}", memberId)
				.accept(MediaType.APPLICATION_JSON));

		// then
		actions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.memberId").value(memberId))
			.andExpect(jsonPath("$.data.email").value(response.getEmail()))
			.andExpect(jsonPath("$.data.name").value(response.getName()))
			.andExpect(jsonPath("$.data.verificationFlag").value(false))
			.andExpect(jsonPath("$.data.deleteFlag").value(false))
			.andExpect(jsonPath("$.data.lastActivateAt").exists())
			.andDo(document("get-member",
				getRequestPreProcessor(),
				getResponsePreProcessor(),
				pathParameters(List.of(
						parameterWithName("member-id").description("회원 식별자")
					)
				),
				responseFields(
					List.of(
						fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
						fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("고유 식별자"),
						fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("data.verificationFlag").type(JsonFieldType.BOOLEAN).description("이메일 인증여부"),
						fieldWithPath("data.deleteFlag").type(JsonFieldType.BOOLEAN).description("탈퇴여부"),
						fieldWithPath("data.lastActivateAt").type(JsonFieldType.STRING).description("마지막 활동일자"),
						fieldWithPath("data.profile").type(JsonFieldType.OBJECT).description("추가 정보"),
						fieldWithPath("data.profile.title").type(JsonFieldType.STRING).description("자기소개 제목"),
						fieldWithPath("data.profile.aboutMe").type(JsonFieldType.STRING).description("자기소개 내용"),
						fieldWithPath("data.profile.location").type(JsonFieldType.STRING).description("지역"),
						fieldWithPath("data.profile.websiteLink").type(JsonFieldType.STRING).description("홈페이지 링크"),
						fieldWithPath("data.profile.twitterLink").type(JsonFieldType.STRING).description("트위터 링크"),
						fieldWithPath("data.profile.githubLink").type(JsonFieldType.STRING).description("깃헙 링크")
					)
				)
			));
	}

	@Test
	void patchMemberTest() throws Exception {
		// given
		long memberId = 1L;
		MemberDto.Patch requestBody = (MemberDto.Patch)StubData.MockMember.getRequestBody(HttpMethod.PATCH);
		requestBody.setMemberId(memberId);
		MemberDto.Response responseDto = StubData.MockMember.getSingleResponseBody();
		String content = gson.toJson(requestBody);

		given(mapper.memberPatchDtoToMember(Mockito.any(MemberDto.Patch.class))).willReturn(new Member());
		given(memberService.updateMember(Mockito.any(Member.class))).willReturn(new Member());
		given(mapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(responseDto);

		// when
		ResultActions actions = mockMvc.perform(
			patch("/members/{member-id}", memberId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(content));

		// then
		actions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.memberId").value(requestBody.getMemberId()))
			.andExpect(jsonPath("$.data.name").value(requestBody.getName()))
			.andDo(document("patch-member",
				getRequestPreProcessor(),
				getResponsePreProcessor(),
				pathParameters(List.of(
					parameterWithName("member-id").description("회원 식별자"))),
				requestFields(
					// TODO : 프로필 객체 생성에 따른 수정 필요
					List.of(
						fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
						fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").optional(),
						fieldWithPath("name").type(JsonFieldType.STRING).description("이름").optional(),
						fieldWithPath("profile").type(JsonFieldType.OBJECT).description("추가 정보").optional(),
						fieldWithPath("profile.title").type(JsonFieldType.STRING).description("자기소개 제목").optional(),
						fieldWithPath("profile.aboutMe").type(JsonFieldType.STRING).description("자기소개 내용").optional(),
						fieldWithPath("profile.location").type(JsonFieldType.STRING).description("지역").optional(),
						fieldWithPath("profile.websiteLink").type(JsonFieldType.STRING).description("홈페이지 링크").optional(),
						fieldWithPath("profile.twitterLink").type(JsonFieldType.STRING).description("트위터 링크").optional(),
						fieldWithPath("profile.githubLink").type(JsonFieldType.STRING).description("깃헙 링크").optional()
					)
				),
				responseFields(
					List.of(
						fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
						fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("고유 식별자"),
						fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("data.verificationFlag").type(JsonFieldType.BOOLEAN).description("이메일 인증여부"),
						fieldWithPath("data.deleteFlag").type(JsonFieldType.BOOLEAN).description("탈퇴여부"),
						fieldWithPath("data.lastActivateAt").type(JsonFieldType.STRING).description("마지막 활동일자"),
						fieldWithPath("data.profile").type(JsonFieldType.OBJECT).description("추가 정보"),
						fieldWithPath("data.profile.title").type(JsonFieldType.STRING).description("자기소개 제목"),
						fieldWithPath("data.profile.aboutMe").type(JsonFieldType.STRING).description("자기소개 내용"),
						fieldWithPath("data.profile.location").type(JsonFieldType.STRING).description("지역"),
						fieldWithPath("data.profile.websiteLink").type(JsonFieldType.STRING).description("홈페이지 링크"),
						fieldWithPath("data.profile.twitterLink").type(JsonFieldType.STRING).description("트위터 링크"),
						fieldWithPath("data.profile.githubLink").type(JsonFieldType.STRING).description("깃헙 링크")
					)
				)));
	}

	@Test
	public void deleteMemberTest() throws Exception {
		// given
		long memberId = 1L;
		doNothing().when(memberService).deleteMember(Mockito.anyLong());

		// when
		ResultActions actions = mockMvc.perform(
			delete("/members/{member-id}", memberId));

		// then
		actions.andExpect(status().isNoContent())
			.andDo(document("delete-member",
				getRequestPreProcessor(),
				getResponsePreProcessor(),
				pathParameters(List.of(parameterWithName("member-id").description("회원 식별자")))
			));
	}
}