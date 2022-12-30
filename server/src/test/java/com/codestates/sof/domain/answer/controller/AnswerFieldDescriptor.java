package com.codestates.sof.domain.answer.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class AnswerFieldDescriptor {
	public static FieldDescriptor[] answerResponseFields = new FieldDescriptor[] {
		fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("고유 식별자"),
		fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 id"),
		fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자 id"),
		fieldWithPath("content").type(JsonFieldType.STRING).description("답변 본문"),
		fieldWithPath("voteCount").type(JsonFieldType.NUMBER).description("투표 횟수"),
		fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("답변 채택여부"),
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

	public static FieldDescriptor[] answerCommentResponseFields = new FieldDescriptor[] {
		fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("고유 식별자"),
		fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 id"),
		fieldWithPath("member").type(JsonFieldType.OBJECT).description("회원 정보"),
		fieldWithPath("member.memberId").type(JsonFieldType.NUMBER).description("고유 식별자"),
		fieldWithPath("member.email").type(JsonFieldType.STRING).description("이메일"),
		fieldWithPath("member.name").type(JsonFieldType.STRING).description("이름"),
		fieldWithPath("member.verificationFlag").type(JsonFieldType.BOOLEAN).description("이메일 인증여부"),
		fieldWithPath("member.deleteFlag").type(JsonFieldType.BOOLEAN).description("탈퇴여부"),
		fieldWithPath("member.lastActivateAt").type(JsonFieldType.STRING).description("마지막 활동일자"),
		fieldWithPath("member.profile").type(JsonFieldType.OBJECT).description("추가 정보"),
		fieldWithPath("member.profile.title").type(JsonFieldType.STRING).description("자기소개 제목"),
		fieldWithPath("member.profile.aboutMe").type(JsonFieldType.STRING).description("자기소개 내용"),
		fieldWithPath("member.profile.location").type(JsonFieldType.STRING).description("지역"),
		fieldWithPath("member.profile.websiteLink").type(JsonFieldType.STRING).description("홈페이지 링크"),
		fieldWithPath("member.profile.twitterLink").type(JsonFieldType.STRING).description("트위터 링크"),
		fieldWithPath("member.profile.githubLink").type(JsonFieldType.STRING).description("깃헙 링크"),
		fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용"),
		fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
		fieldWithPath("lastModifiedAt").type(JsonFieldType.STRING).description("마지막 수정일자")
	};

	public static FieldDescriptor[] answerVoteResponseFields = new FieldDescriptor[] {
		fieldWithPath("answerVoteId").type(JsonFieldType.NUMBER).description("고유 식별자"),
		fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자 id"),
		fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 id"),
		fieldWithPath("voteType").type(JsonFieldType.STRING).description("투표 타입 [UP, DOWN, NONE]"),
		fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
		fieldWithPath("lastModifiedAt").type(JsonFieldType.STRING).description("마지막 수정일자")
	};
}
