package com.codestates.sof.domain.question.controller;

import static com.codestates.sof.domain.question.controller.QuestionControllerRestDocsTest.*;
import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.sof.domain.question.mapper.QuestionMapper;
import com.codestates.sof.domain.question.service.BookmarkService;
import com.codestates.sof.domain.stub.QuestionStub;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@ActiveProfiles("local")
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = BookmarkController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class BookmarkControllerRestDocsTest {
	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper om;

	@MockBean
	QuestionMapper mapper;

	@MockBean
	BookmarkService service;

	@Test
	void testForPost() throws Exception {
		// given
		given(service.bookmark(any(), anyLong(), anyBoolean())).willReturn(true);

		// when
		ResultActions actions = mvc.perform(
			post("/questions/{question-id}/bookmarks", 1L)
				.param("inUndo", "false")
				.header("Authorization", "Required JWT access token")
		);

		// then
		actions
			.andExpect(status().isCreated())
			.andDo(
				getDefaultDocument(
					"bookmark/post",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					requestParameters(parameterWithName("inUndo").description("취소여부 [true, false]").optional()),
					pathParameters(parameterWithName("question-id").description("질문 식별자"))
				)
			);
	}

	@Test
	void testForGetAll() throws Exception {
		// given
		given(service.getAll(any(), anyInt(), anyInt())).willReturn(new PageImpl<>(Collections.emptyList()));
		given(mapper.questionsToResponses(any(), any())).willReturn(List.of(
			QuestionStub.getSimpleResponse(),
			QuestionStub.getSimpleResponse()
		));

		// when
		ResultActions actions = mvc.perform(
			get("/members/bookmarks")
				.param("page", "1")
				.param("size", "10")
				.header("Authorization", "Required JWT access token")
		);

		// then
		actions
			.andExpect(status().isOk())
			.andDo(
				getDefaultDocument(
					"bookmark/getAll",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					requestParameters(
						parameterWithName("page").description("요청 페이지 (default: 1)").optional(),
						parameterWithName("size").description("요청 개수 (default: 10)").optional()
					),
					getMultiResponseSnippet()
				)
			);
	}

}