package com.codestates.sof.domain.answer.controller;

import static com.codestates.sof.domain.answer.controller.AnswerFieldDescriptor.*;
import static com.codestates.sof.domain.stub.AnswerStubData.MockAnswerComment.*;
import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.sof.domain.answer.dto.AnswerCommentDto;
import com.codestates.sof.domain.answer.entity.AnswerComment;
import com.codestates.sof.domain.answer.mapper.AnswerCommentMapper;
import com.codestates.sof.domain.answer.service.AnswerCommentService;
import com.google.gson.Gson;

@WebMvcTest(controllers = AnswerCommentController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class AnswerCommentControllerRestDocsTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Gson gson;

	@MockBean
	private AnswerCommentService commentService;

	@MockBean
	private AnswerCommentMapper commentMapper;

	@Test
	public void postAnswerCommentTest() throws Exception {
		// given
		long answerId = 1L;
		AnswerCommentDto.Post post = getAnswerCommentPostDto();
		String content = gson.toJson(post);

		AnswerCommentDto.Response response = getAnswerCommentResponseDto();

		given(commentMapper.postToComment(any(AnswerCommentDto.Post.class))).willReturn(new AnswerComment());
		given(commentService.createComment(any(AnswerComment.class), anyLong())).willReturn(new AnswerComment());
		given(commentMapper.commentToResponse(any(AnswerComment.class))).willReturn(response);

		// when
		ResultActions actions =
			mockMvc.perform(
				post("/answers/{answer-id}/comments", answerId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		// then
		actions
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.member.memberId").value(post.getMemberId()))
			.andExpect(jsonPath("$.data.content").value(post.getContent()))
			.andDo(document("post-answer-comment",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					pathParameters(
						parameterWithName("answer-id").description("답변 id")),
					requestFields(
						fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 id"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")),
					responseFields(
						fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"))
						.andWithPrefix("data.", answerCommentResponseFields)
				)
			);
	}

	@Test
	public void patchAnswerCommentTest() throws Exception {
		// given
		long answerId = 1L;
		long commentId = 1L;
		AnswerCommentDto.Patch patch = getAnswerCommentPatchDto();
		String content = gson.toJson(patch);

		AnswerCommentDto.Response response = getAnswerCommentResponseDto();

		given(commentMapper.patchToComment(any(AnswerCommentDto.Patch.class))).willReturn(new AnswerComment());
		given(commentService.updateComment(any(AnswerComment.class), anyLong())).willReturn(new AnswerComment());
		given(commentMapper.commentToResponse(any(AnswerComment.class))).willReturn(response);

		// when
		ResultActions actions =
			mockMvc.perform(
				patch("/answers/{answer-id}/comments/{comment-id}", answerId, commentId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		// then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.member.memberId").value(patch.getMemberId()))
			.andExpect(jsonPath("$.data.content").value(patch.getContent()))
			.andDo(document("patch-answer-comment",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					pathParameters(
						parameterWithName("answer-id").description("답변 id"),
						parameterWithName("comment-id").description("고유 식별자")),
					requestFields(
						fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자 id"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")),
					responseFields(
						fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"))
						.andWithPrefix("data.", answerCommentResponseFields)
				)
			);
	}

	@Test
	public void getAnswerCommentsTest() throws Exception {
		// given
		long answerId = 1L;
		Page<AnswerComment> pageComments = getPageAnswerComments();
		List<AnswerCommentDto.Response> responses = getMultiAnswerCommentResponseDto();

		given(commentService.findComments(anyInt(), anyInt())).willReturn(pageComments);
		given(commentMapper.commentsToResponses(anyList())).willReturn(responses);

		// when
		ResultActions actions =
			mockMvc.perform(
				get("/answers/{answer-id}/comments", answerId)
					.accept(MediaType.APPLICATION_JSON)
			);

		// then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data.length()").value(responses.size()))
			.andDo(document("get-answer-comments",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					pathParameters(
						parameterWithName("answer-id").description("답변 id")),
					responseFields(
						fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"))
						.andWithPrefix("data[].", answerCommentResponseFields)
						.andWithPrefix("pageInfo.", pageResponseFields)
				)
			);
	}

	@Test
	public void deleteAnswerCommentTest() throws Exception {
		// given
		long answerId = 1L;
		long commentId = 1L;

		doNothing().when(commentService).deleteComment(commentId);

		// when
		ResultActions actions =
			mockMvc.perform(
				delete("/answers/{answer-id}/comments/{comment-id}", answerId, commentId)
			);

		// then
		actions
			.andExpect(status().isNoContent())
			.andDo(document("delete-answer-comment",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					pathParameters(
						parameterWithName("answer-id").description("답변 id"),
						parameterWithName("comment-id").description("고유 식별자")
					)
				)
			);
	}
}
