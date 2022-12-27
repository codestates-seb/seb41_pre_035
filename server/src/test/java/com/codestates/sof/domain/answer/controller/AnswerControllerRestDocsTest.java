package com.codestates.sof.domain.answer.controller;

import static com.codestates.sof.domain.answer.controller.AnswerFieldDescriptor.*;
import static com.codestates.sof.domain.answer.controller.AnswerStubData.MockAnswer.*;
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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.sof.domain.answer.dto.AnswerDto;
import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.mapper.AnswerMapper;
import com.codestates.sof.domain.answer.service.AnswerService;
import com.google.gson.Gson;

@WebMvcTest(AnswerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class AnswerControllerRestDocsTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Gson gson;

	@MockBean
	private AnswerService answerService;

	@MockBean
	private AnswerMapper answerMapper;

	@Test
	public void postAnswerTest() throws Exception {
		// given
		long questionId = 1L;

		AnswerDto.Post post = getAnswerPostDto();
		String content = gson.toJson(post);

		AnswerDto.Response response = getAnswerResponseDto();

		given(answerMapper.postToAnswer(any(AnswerDto.Post.class))).willReturn(new Answer());
		given(answerService.createAnswer(any(Answer.class))).willReturn(new Answer());
		given(answerMapper.answerToResponse(any(Answer.class))).willReturn(response);

		// when
		ResultActions actions =
			mockMvc.perform(
				post("/answers")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		// then
		actions
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.writerId").value(post.getWriterId()))
			.andExpect(jsonPath("$.data.content").value(post.getContent()))
			.andDo(document("post-answer",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					requestFields(
						fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 id"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("답변 본문")),
					responseFields(
						fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"))
						.andWithPrefix("data.", answerResponseFields)
				)
			);
	}

	@Test
	public void patchAnswerTest() throws Exception {
		// given
		Long answerId = 1L;
		AnswerDto.Patch patch = getAnswerPatchDto();
		String content = gson.toJson(patch);

		AnswerDto.Response response = getAnswerResponseDto();

		given(answerMapper.patchToAnswer(any(AnswerDto.Patch.class))).willReturn(new Answer());
		given(answerService.updateAnswer(any(Answer.class))).willReturn(new Answer());
		given(answerMapper.answerToResponse(any(Answer.class))).willReturn(response);

		// when
		ResultActions actions =
			mockMvc.perform(
				patch("/answers/{answer-id}", answerId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		// then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.answerId").value(patch.getAnswerId()))
			.andExpect(jsonPath("$.data.content").value(patch.getContent()))
			.andDo(document("patch-answer",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					pathParameters(
						parameterWithName("answer-id").description("고유 식별자")
					),
					requestFields(
						fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("고유 식별자"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("답변 본문")
					),
					responseFields(
						fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"))
						.andWithPrefix("data.", answerResponseFields)
				)
			);
	}

	@Test
	public void getAnswersTest() throws Exception {
		// given
		Page<Answer> pageAnswers = getPageAnswers();
		List<AnswerDto.Response> responses = getMultiAnswerResponseDto();

		given(answerService.findAnswers(anyInt(), anyInt())).willReturn(pageAnswers);
		given(answerMapper.answersToResponses(anyList())).willReturn(responses);

		// when
		ResultActions actions =
			mockMvc.perform(
				get("/answers")
					.param("page", String.valueOf(pageAnswers.getNumber() + 1))
					.accept(MediaType.APPLICATION_JSON)
			);

		// then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data.length()").value(responses.size()))
			.andDo(document("get-answers",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					requestParameters(
						parameterWithName("page").description("페이지 번호")),
					responseFields(
						fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
						fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"))
						.andWithPrefix("data[].", answerResponseFields)
						.andWithPrefix("pageInfo.", pageResponseFields)
				)
			);
	}

	@Test
	public void deleteAnswerTest() throws Exception {
		// given
		long answerId = 1L;

		doNothing().when(answerService).deleteAnswer(answerId);

		// when
		ResultActions actions =
			mockMvc.perform(
				delete("/answers/{answer-id}", answerId)
			);

		// then
		actions
			.andExpect(status().isNoContent())
			.andDo(document("delete-answer",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					pathParameters(
						parameterWithName("answer-id").description("고유 식별자")
					)
				)
			);
	}
}
