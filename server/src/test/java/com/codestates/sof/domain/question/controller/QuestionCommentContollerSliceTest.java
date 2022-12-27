package com.codestates.sof.domain.question.controller;

import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.sof.domain.question.dto.QuestionCommentDto;
import com.codestates.sof.domain.question.entity.QuestionComment;
import com.codestates.sof.domain.question.mapper.QuestionCommentMapper;
import com.codestates.sof.domain.question.service.QuestionCommentService;
import com.codestates.sof.domain.stub.QuestionCommentStub;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@ActiveProfiles("local")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(QuestionCommentContoller.class)
class QuestionCommentContollerSliceTest {
	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper om;

	@MockBean
	QuestionCommentMapper mapper;

	@MockBean
	QuestionCommentService service;

	QuestionCommentDto.Post post;
	QuestionCommentDto.Patch patch;
	QuestionCommentDto.Response response;

	@BeforeEach
	void beforeEach() {
		post = QuestionCommentStub.getDefaultPost();
		patch = QuestionCommentStub.getDefaultPatch();
		response = QuestionCommentStub.getDefaultResponse(false);
	}

	@Test
	void testForPost() throws Exception {
		// given
		QuestionComment temporalEntity = new QuestionComment(null, null, null);
		given(mapper.postToQuestionComment(any(QuestionCommentDto.Post.class))).willReturn(temporalEntity);
		given(mapper.questionCommentToResponse(any(QuestionComment.class))).willReturn(response);
		given(service.comment(anyLong(), any(QuestionComment.class))).willReturn(temporalEntity);

		// when
		ResultActions actions = mvc.perform(
			post("/questions/{question-id}/comments", 1L)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(post))
		);

		// then
		actions
			.andExpect(status().isCreated())
			.andDo(
				getDefaultDocument(
					"question-comments/post",
					pathParameters(
						parameterWithName("question-id").description("질문 식별자")
					),
					requestFields(
						List.of(
							fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자의 식별자"),
							fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
						)
					),
					getResponseFieldsSnippet()
				)
			);
	}

	@Test
	void testForPatch() throws Exception {
		// given
		QuestionComment temporalEntity = new QuestionComment(null, null, null);
		given(mapper.patchToQuestionComment(any(QuestionCommentDto.Patch.class))).willReturn(temporalEntity);
		given(mapper.questionCommentToResponse(any(QuestionComment.class))).willReturn(response);
		given(service.modify(anyLong(), anyLong(), any(QuestionComment.class))).willReturn(temporalEntity);

		// when
		ResultActions actions = mvc.perform(
			patch("/questions/{question-id}/comments/{comment-id}", 1L, 1L)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(patch))
		);

		// then
		actions
			.andExpect(status().isOk())
			.andDo(
				getDefaultDocument(
					"question-comments/patch",
					pathParameters(
						parameterWithName("question-id").description("질문 식별자"),
						parameterWithName("comment-id").description("댓글 식별자")
					),
					requestFields(
						List.of(
							fieldWithPath("modifierId").type(JsonFieldType.NUMBER).description("수정을 시도한 회원의 식별자"),
							fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
						)
					),
					getResponseFieldsSnippet()
				)
			);
	}

	@Test
	void testForDelete() throws Exception {
		// given
		willDoNothing().given(service).delete(anyLong(), anyLong(), anyLong());

		// when
		ResultActions actions = mvc.perform(
			delete("/questions/{question-id}/comments/{comment-id}", 1L, 1L)
		);

		// then
		actions
			.andExpect(status().isNoContent())
			.andDo(
				getDefaultDocument(
					"question-comments/delete",
					pathParameters(
						parameterWithName("question-id").description("질문 식별자"),
						parameterWithName("comment-id").description("댓글 식별자")
					)
				)
			);
	}

	private ResponseFieldsSnippet getResponseFieldsSnippet() {
		return responseFields(
			List.of(
				fieldWithPath("data.member").type(JsonFieldType.OBJECT).description("댓글 작성자의 정보"),
				fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("작성자의 식별자"),
				fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("작성자 이메일"),
				fieldWithPath("data.member.name").type(JsonFieldType.STRING).description("작성자 이름"),
				fieldWithPath("data.member.verificationFlag").type(JsonFieldType.BOOLEAN).description("인증여부"),
				fieldWithPath("data.member.deleteFlag").type(JsonFieldType.BOOLEAN).description("탈퇴여부"),
				fieldWithPath("data.member.lastActivateAt").type(JsonFieldType.STRING).description("마지막 활동시간"),
				fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문의 식별자"),
				fieldWithPath("data.content").type(JsonFieldType.STRING).description("댓글 내용"),
				fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("댓글 등록일자"),
				fieldWithPath("data.lastModifiedAt").type(JsonFieldType.STRING).description("마지막 댓글 수정일자")
			)
		);
	}
}