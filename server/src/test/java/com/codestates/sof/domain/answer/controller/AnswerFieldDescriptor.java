package com.codestates.sof.domain.answer.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class AnswerFieldDescriptor {
	public static FieldDescriptor[] answerResponseFields = new FieldDescriptor[] {
		fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("고유 식별자"),
		fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 id"),
		fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 id"),
		fieldWithPath("content").type(JsonFieldType.STRING).description("답변 본문"),
		fieldWithPath("voteCount").type(JsonFieldType.NUMBER).description("투표 횟수"),
		fieldWithPath("isItWriter").type(JsonFieldType.BOOLEAN).description("작성자 여부"),
		fieldWithPath("hasAlreadyVoted").type(JsonFieldType.BOOLEAN).description("투표 여부"),
		fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
		fieldWithPath("lastModifiedAt").type(JsonFieldType.STRING).description("마지막 수정일자")
	};

	public static FieldDescriptor[] pageResponseFields = new FieldDescriptor[] {
		fieldWithPath("page").type(JsonFieldType.NUMBER).description("현재 페이지"),
		fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지당 출력 수"),
		fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("총 내용 수"),
		fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
	};
}
