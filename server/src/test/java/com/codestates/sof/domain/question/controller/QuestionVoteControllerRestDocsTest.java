package com.codestates.sof.domain.question.controller;

import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.sof.domain.common.VoteType;
import com.codestates.sof.domain.question.service.QuestionVoteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@ActiveProfiles("local")
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = QuestionVoteController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class QuestionVoteControllerRestDocsTest {
	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper om;

	@MockBean
	QuestionVoteService service;

	@Test
	void testForPatch() throws Exception {
		// given
		given(service.update(any(), anyLong(), any(VoteType.class))).willReturn(5);

		// when
		ResultActions actions = mvc.perform(
			patch("/questions/{question-id}/votes", 1L)
				.content(om.writeValueAsString(Collections.singletonMap("voteType", "UP")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Required JWT access token")
		);

		// then
		actions
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(
				getDefaultDocument(
					"question-vote/patch",
					requestHeaders(headerWithName("Authorization").description("Jwt Access Token")),
					pathParameters(parameterWithName("question-id").description("질문 식별자")),
					requestFields(
						fieldWithPath("voteType").type(JsonFieldType.STRING).description("투표 타입 [UP, DOWN, NONE]")
					),
					responseFields(fieldWithPath("data.voteCount").type(JsonFieldType.NUMBER).description("질문의 투표수 총합"))
				)
			);
	}
}