package com.codestates.sof.domain.answer.controller;

import static com.codestates.sof.domain.answer.controller.AnswerFieldDescriptor.*;
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
import com.codestates.sof.domain.answer.entity.AnswerVote;
import com.codestates.sof.domain.answer.mapper.AnswerVoteMapper;
import com.codestates.sof.domain.answer.service.AnswerVoteService;
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
	private AnswerVoteService answerVoteService;

	@MockBean
	private AnswerVoteMapper voteMapper;

	@Test
	public void patchAnswerVoteTest() throws Exception {
		// given
		Long answerId = 1L;
		AnswerVoteDto.Patch patch = getAnswerVotePatchDto();
		String content = gson.toJson(patch);

		AnswerVoteDto.Response response = getAnswerVoteResponseDto();

		given(voteMapper.patchToAnswerVote(any(AnswerVoteDto.Patch.class))).willReturn(new AnswerVote());
		given(answerVoteService.updateAnswerVote(any(AnswerVote.class), any(), anyLong())).willReturn(new AnswerVote());
		given(voteMapper.answerVoteToResponse(any(AnswerVote.class))).willReturn(response);

		// when
		ResultActions actions =
			mockMvc.perform(
				patch("/answers/{answer-id}/votes", answerId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			);

		// then
		actions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.memberId").value(patch.getMemberId()))
			.andExpect(jsonPath("$.data.voteType").value(patch.getVoteType().name()))
			.andDo(document("patch-answer-vote",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					pathParameters(
						parameterWithName("answer-id").description("답변 id")
					),
					requestFields(
						fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자 id"),
						fieldWithPath("voteType").type(JsonFieldType.STRING).description("투표 타입 [UP, DOWN, NONE]")
					),
					responseFields(
						fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터")
					).andWithPrefix("data.", answerVoteResponseFields)
				)
			);
	}
}
