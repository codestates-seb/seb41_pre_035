package com.codestates.sof.domain.question.controller;

import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
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

import com.codestates.sof.domain.question.dto.QuestionDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.mapper.QuestionMapper;
import com.codestates.sof.domain.question.service.QuestionService;
import com.codestates.sof.global.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@ActiveProfiles("local")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest({QuestionController.class, QuestionMapper.class})
class QuestionControllerSliceTest {
	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper om;

	@Autowired
	QuestionMapper mapper;

	@MockBean
	QuestionService service;

	@Test
	void testForWrite() throws Exception {
		// given
		Question question = getDefaultQuestion();
		QuestionDto.Post post = new QuestionDto.Post();
		post.setWriterId(1L);
		post.setTitle("title");
		post.setContent("content");

		// when
		when(service.write(any(Question.class))).thenReturn(question);

		ResultActions actions = mvc.perform(TestUtils.POST.apply("/questions", om.writeValueAsString(post)));

		// then
		actions
			.andDo(print())
			.andExpect(jsonPath("$.questionId").value(1))
			.andExpect(jsonPath("$.writerId").value(1))
			.andExpect(jsonPath("$.title").value("title"))
			.andExpect(jsonPath("$.content").value("content"))
			.andExpect(jsonPath("$.viewCount").value(0))
			.andExpect(jsonPath("$.voteCount").value(0))
			.andExpect(jsonPath("$.isItWriter").value(true))
			.andExpect(jsonPath("$.hasAlreadyVoted").value(false))
			.andExpect(jsonPath("$.createdAt").exists())
			.andExpect(jsonPath("$.lastModifiedAt").exists())
			.andDo(getDefaultDocument(
				"question-post",
				requestFields(
					List.of(
						fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 id"),
						fieldWithPath("title").type(JsonFieldType.STRING).description("질문 제목"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("질문 내용")
					)
				),
				responseFields(
					List.of(
						fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("생성된 질문 id"),
						fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 id"),
						fieldWithPath("title").type(JsonFieldType.STRING).description("질문 제목"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("질문 내용"),
						fieldWithPath("viewCount").type(JsonFieldType.NUMBER).description("조회수"),
						fieldWithPath("voteCount").type(JsonFieldType.NUMBER).description("투표수 총합"),
						fieldWithPath("isItWriter").type(JsonFieldType.BOOLEAN).description("작성자여부"),
						fieldWithPath("hasAlreadyVoted").type(JsonFieldType.BOOLEAN).description("투표여부"),
						fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
						fieldWithPath("lastModifiedAt").type(JsonFieldType.STRING).description("마지막 수정일자")
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
			.andExpect(jsonPath("$.data.writerId").value(question.getWriterId()))
			.andExpect(jsonPath("$.data.lastModifiedAt").exists());
	}

	private Question getDefaultQuestion() {
		return Question.Builder.aQuestion()
			.questionId(1L)
			.writerId(1L)
			.title("title")
			.content("content")
			.viewCount(0)
			.voteCount(0)
			.build();
	}
}