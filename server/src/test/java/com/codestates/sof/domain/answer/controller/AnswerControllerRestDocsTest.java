package com.codestates.sof.domain.answer.controller;

import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

		AnswerDto.Post post = new AnswerDto.Post("답변입니다.");
		String content = gson.toJson(post);

		AnswerDto.Response response =
			new AnswerDto.Response(1L, "답변입니다.", 0);

		given(answerMapper.answerPostToAnswer(any(AnswerDto.Post.class))).willReturn(new Answer());
		given(answerService.createAnswer(any(Answer.class))).willReturn(new Answer());
		given(answerMapper.answerToAnswerResponse(any(Answer.class))).willReturn(response);

		// when
		ResultActions actions =
			mockMvc.perform(
				post("/answers")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		// then : --Request, Response PreProcessor 만들기-- / SingleResponseDto, MultiResponseDto, PageInfo 만들기
		actions
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.content").value(post.getContent()))
			.andDo(document("post-answer",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					requestFields(
						fieldWithPath("content").type(JsonFieldType.STRING).description("답변 본문")),
					responseFields(
						fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("고유 식별자"),
						fieldWithPath("data.content").type(JsonFieldType.STRING).description("답변 본문"),
						fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("투표 횟수")
					)
				)
			);
	}
}
