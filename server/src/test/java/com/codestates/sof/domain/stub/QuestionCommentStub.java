package com.codestates.sof.domain.stub;

import java.time.LocalDateTime;

import com.codestates.sof.domain.member.controller.StubData;
import com.codestates.sof.domain.member.dto.MemberDto;
import com.codestates.sof.domain.question.dto.QuestionCommentRequestDto;
import com.codestates.sof.domain.question.dto.QuestionCommentResponseDto;

public class QuestionCommentStub {
	public static QuestionCommentRequestDto.Post getDefaultPost() {
		QuestionCommentRequestDto.Post post = new QuestionCommentRequestDto.Post();
		post.setContent("comment");
		return post;
	}

	public static QuestionCommentRequestDto.Patch getDefaultPatch() {
		QuestionCommentRequestDto.Patch patch = new QuestionCommentRequestDto.Patch();
		patch.setContent("modified-comment");
		return patch;
	}

	public static QuestionCommentResponseDto.Response getDefaultResponse(boolean isModifiedComment) {
		MemberDto.Response member = StubData.MockMember.getSingleResponseBody();
		member.setProfile(null);
		QuestionCommentResponseDto.Response response = new QuestionCommentResponseDto.Response();
		response.setQuestionId(1L);
		response.setMember(member);
		response.setContent(isModifiedComment ? "modified-comment" : "comment");
		response.setCreatedAt(LocalDateTime.now());
		response.setLastModifiedAt(LocalDateTime.now());
		return response;
	}

	public static QuestionCommentResponseDto.SimpleResponse getSimpleResponse() {
		MemberDto.Response member = StubData.MockMember.getSingleResponseBody();
		QuestionCommentResponseDto.SimpleResponse response = new QuestionCommentResponseDto.SimpleResponse();
		response.setQuestionId(1L);
		response.setMemberId(member.getMemberId());
		response.setMemberName(member.getName());
		response.setContent("comment");
		response.setCreatedAt(LocalDateTime.now());
		response.setLastModifiedAt(LocalDateTime.now());
		return response;
	}
}
