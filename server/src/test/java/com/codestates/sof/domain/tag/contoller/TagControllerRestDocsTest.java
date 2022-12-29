package com.codestates.sof.domain.tag.contoller;

import static com.codestates.sof.global.utils.AsciiUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.codestates.sof.domain.stub.TagStub;
import com.codestates.sof.domain.tag.entity.Tag;
import com.codestates.sof.domain.tag.mapper.TagMapper;
import com.codestates.sof.domain.tag.service.TagService;
import com.codestates.sof.domain.tag.support.TagPageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@ActiveProfiles("local")
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(value = TagController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class TagControllerRestDocsTest {
	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper om;

	@MockBean
	TagService service;

	@MockBean
	TagMapper mapper;

	@Test
	void testForGetAll() throws Exception {
		// given
		given(service.findAll(any(TagPageRequest.class)))
			.willReturn(new PageImpl<>(List.of(new Tag("tag1"), new Tag("tag2"))));
		given(mapper.tagToResponse(any(Tag.class)))
			.willReturn(TagStub.responses().get(0));

		// when
		ResultActions actions = mvc.perform(
			get("/tags")
				.param("page", "1")
				.param("size", "20")
				.param("sort", "POPULAR")
		);

		// then
		actions
			.andExpect(status().isOk())
			.andDo(
				getDefaultDocument(
					"tag/get-all",
					requestParameters(
						parameterWithName("page").description("요청 페이지 번호 (default: 1)").optional(),
						parameterWithName("size").description("요청 태그 개수 (default:30)").optional(),
						parameterWithName("sort").description("정렬 타입 [POPULAR | NAME | NEWEST]").optional()
					),
					responseFields(
						fieldWithPath("data").type(JsonFieldType.ARRAY).description("질문 정보"),
						fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보")
					).andWithPrefix("data.[].",
						fieldWithPath("tagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
						fieldWithPath("name").type(JsonFieldType.STRING).description("태그명"),
						fieldWithPath("description").type(JsonFieldType.STRING).description("태그 설명"),
						fieldWithPath("taggedCount").type(JsonFieldType.NUMBER).description("태그된 수")
					).andWithPrefix("pageInfo.",
						fieldWithPath("page").type(JsonFieldType.NUMBER).description("요청한 페이지"),
						fieldWithPath("size").type(JsonFieldType.NUMBER).description("요청한 개수"),
						fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
						fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 개수")
					)
				)
			);
	}
}