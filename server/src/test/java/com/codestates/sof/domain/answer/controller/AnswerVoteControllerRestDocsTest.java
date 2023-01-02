package com.codestates.sof.domain.answer.controller;

import static com.codestates.sof.domain.stub.AnswerStubData.MockAnswerVote.*;
import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.sof.domain.answer.dto.AnswerVoteDto;
import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.service.AnswerService;
import com.google.gson.Gson;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AnswerVoteController.class,
	excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class AnswerVoteControllerRestDocsTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Gson gson;

	@MockBean
	private AnswerService answerService;

	@Test
	public void patchAnswerVoteTest() throws Exception {
		// given
		Long answerId = 1L;
		AnswerVoteDto.Patch patch = getAnswerVotePatchDto();
		String content = gson.toJson(patch);

		given(answerService.updateAnswerVote(any(AnswerVoteDto.Patch.class), any(), anyLong())).willReturn(
			new Answer());

		// when
		ResultActions actions =
			mockMvc.perform(
				patch("/answers/{answer-id}/votes", answerId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		// then
		actions
			.andExpect(status().isOk())
			.andDo(document("patch-answer-vote",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					pathParameters(
						parameterWithName("answer-id").description("답변 id")
					),
					requestFields(
						fieldWithPath("voteType").type(JsonFieldType.STRING).description("투표 타입 [UP, DOWN, NONE]")
					)
				)
			);
	}
}
