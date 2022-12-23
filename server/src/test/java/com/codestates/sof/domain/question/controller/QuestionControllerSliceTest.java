package com.codestates.sof.domain.question.controller;

import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.dto.QuestionDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.mapper.QuestionMapper;
import com.codestates.sof.domain.question.service.QuestionService;
import com.codestates.sof.domain.stub.QuestionStub;
import com.codestates.sof.global.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@ActiveProfiles("local")
@MockBean(JpaMetamodelMappingContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest({QuestionController.class, QuestionMapper.class})
class QuestionControllerSliceTest {
	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper om;

	@MockBean
	QuestionMapper mapper;

	@MockBean
	QuestionService service;

	static Question question = (Question)QuestionStub.Type.DEFAULT.getData();
	static QuestionDto.Post post = (QuestionDto.Post)QuestionStub.Type.POST.getData();
	static QuestionDto.Response response = (QuestionDto.Response)QuestionStub.Type.RESPONSE.getData();

	@Test
	void testForWrite() throws Exception {
		// given
		given(service.write(any(Question.class))).willReturn(question);
		given(mapper.questionToResponse(any(Question.class))).willReturn(response);
		given(mapper.postToQuestion(any(QuestionDto.Post.class))).willReturn(question);

		// when
		ResultActions actions = mvc.perform(TestUtils.POST.apply("/questions", om.writeValueAsString(post)));

		// then
		actions
			.andDo(print())
			.andExpect(jsonPath("$.questionId").value(1))
			.andExpect(jsonPath("$.createdAt").exists())
			.andExpect(jsonPath("$.lastModifiedAt").exists())
			.andDo(getDefaultDocument(
				"question-post",
				requestFields(
					List.of(
						fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자의 식별자"),
						fieldWithPath("title").type(JsonFieldType.STRING).description("질문 제목"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("질문 내용"),
						fieldWithPath("tags").type(JsonFieldType.ARRAY).description("질문에 등록된 태그들의 이름")
					)
				),
				responseFields(
					List.of(
						fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("생성된 질문 식별자"),
						fieldWithPath("writer").type(JsonFieldType.OBJECT).description("작성자 정보 (단일 Member 조회 API 응답과 동일)"),
							fieldWithPath("writer.memberId").type(JsonFieldType.NUMBER).description("작성자의 식별자"),
							fieldWithPath("writer.email").type(JsonFieldType.STRING).description("작성자 이메일"),
							fieldWithPath("writer.name").type(JsonFieldType.STRING).description("작성자 이름"),
							fieldWithPath("writer.verificationFlag").type(JsonFieldType.BOOLEAN).description("인증여부"),
							fieldWithPath("writer.deleteFlag").type(JsonFieldType.BOOLEAN).description("삭제여부"),
							fieldWithPath("writer.lastActivateAt").type(JsonFieldType.STRING).description("마지막 활동일자"),
						fieldWithPath("title").type(JsonFieldType.STRING).description("질문 제목"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("질문 내용"),
						fieldWithPath("viewCount").type(JsonFieldType.NUMBER).description("조회수"),
						fieldWithPath("voteCount").type(JsonFieldType.NUMBER).description("투표수 총합"),
						fieldWithPath("isItWriter").type(JsonFieldType.BOOLEAN).description("작성자여부"),
						fieldWithPath("hasAlreadyVoted").type(JsonFieldType.BOOLEAN).description("투표여부"),
						fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
						fieldWithPath("lastModifiedAt").type(JsonFieldType.STRING).description("마지막 수정일자"),
						fieldWithPath("tags").type(JsonFieldType.ARRAY).description("각 태그들의 정보 (단일 Tag 조회 API 응답과 동일)"),
						fieldWithPath("answers").type(JsonFieldType.ARRAY).description("각 답변들의 정보 (단일 Answer 조회 API 응답과 동일)")
					)
				)
			));
	}

	@Test
	void testForGetByAnonymous() throws Exception {
		// given
		Question question = getDefaultQuestion();
		given(service.findById(any(Long.class))).willReturn(question);

		// when
		ResultActions actions = mvc.perform(
			get("/questions/{question-id}", question.getQuestionId())
				.accept(MediaType.APPLICATION_JSON)
		);

		// then
		actions
			.andDo(print())
			.andExpect(jsonPath("$.data.questionId").value(question.getQuestionId()))
			.andExpect(jsonPath("$.data.writerId").value(question.getMember().getMemberId()))
			.andExpect(jsonPath("$.data.lastModifiedAt").exists())
			.andDo(getDefaultDocument(
				"question-get",
				pathParameters(
					parameterWithName("question-id").description("질문 식별자")
				),
				responseFields(
					List.of(
						fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
						fieldWithPath("data.writerId").type(JsonFieldType.NUMBER).description("작성자 식별자"),
						fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문 제목"),
						fieldWithPath("data.content").type(JsonFieldType.STRING).description("질문 내용"),
						fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("조회수"),
						fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("투표수 총합"),
						fieldWithPath("data.isItWriter").type(JsonFieldType.BOOLEAN).description("작성자여부"),
						fieldWithPath("data.hasAlreadyVoted").type(JsonFieldType.BOOLEAN).description("투표여부"),
						fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성일자"),
						fieldWithPath("data.lastModifiedAt").type(JsonFieldType.STRING).description("마지막 수정일자")
					)
				)
			));
	}

	@Test
	void testForPatch() throws Exception {
		// given
		Question question = getDefaultQuestion();
		QuestionDto.Patch patch = new QuestionDto.Patch();
		patch.setMemberId(1L);
		patch.setTitle("title");
		patch.setContent("content");

		given(service.patch(anyLong(), anyLong(), any())).willReturn(question);

		// when
		ResultActions actions = mvc.perform(
			patch("/questions/{question-id}", 1L)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(patch))
		);

		// then
		actions
			.andDo(print())
			.andExpect(jsonPath("$.data.title").value(patch.getTitle()))
			.andExpect(jsonPath("$.data.content").value(patch.getContent()))
			.andDo(getDefaultDocument(
				"question-content",
				pathParameters(
					parameterWithName("question-id").description("질문 식별자")
				),
				requestFields(
					List.of(
						fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("질문 수정을 시도한 사용자의 식별자"),
						fieldWithPath("title").type(JsonFieldType.STRING).description("수정된 질문 제목").optional(),
						fieldWithPath("content").type(JsonFieldType.STRING).description("수정된 질문 내용").optional()
					)
				),
				responseFields(
					List.of(
						fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
						fieldWithPath("data.writerId").type(JsonFieldType.NUMBER).description("작성자 식별자"),
						fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문 제목"),
						fieldWithPath("data.content").type(JsonFieldType.STRING).description("질문 내용"),
						fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("조회수"),
						fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("투표수 총합"),
						fieldWithPath("data.isItWriter").type(JsonFieldType.BOOLEAN).description("작성자여부"),
						fieldWithPath("data.hasAlreadyVoted").type(JsonFieldType.BOOLEAN).description("투표여부"),
						fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성일자"),
						fieldWithPath("data.lastModifiedAt").type(JsonFieldType.STRING).description("마지막 수정일자")
					)
				)
			));
	}

	@Test
	void testForDelete() throws Exception {
		// given
		willDoNothing().given(service).delete(anyLong(), anyLong());

		// when
		ResultActions actions = mvc.perform(delete("/questions/{question-id}", 1L));

		// then
		actions
			.andExpect(status().isNoContent())
			.andDo(getDefaultDocument(
				"question-delete",
				pathParameters(parameterWithName("question-id").description("질문 식별자"))
			));
	}

	private Question getDefaultQuestion() {
		final Member member = new Member();
		member.setMemberId(1L);

		return Question.Builder.aQuestion()
			.questionId(1L)
			.writer(member)
			.title("title")
			.content("content")
			.viewCount(0)
			.voteCount(0)
			.build();
	}
}