package com.codestates.sof.domain.stub;

import java.time.LocalDateTime;

import com.codestates.sof.domain.member.controller.StubData;
import com.codestates.sof.domain.question.dto.QuestionCommentDto;

public class QuestionCommentStub {
	public static QuestionCommentDto.Post getDefaultPost() {
		QuestionCommentDto.Post post = new QuestionCommentDto.Post();
		post.setMemberId(1L);
		post.setContent("comment");
		return post;
	}

	public static QuestionCommentDto.Patch getDefaultPatch() {
		QuestionCommentDto.Patch patch = new QuestionCommentDto.Patch();
		patch.setModifierId(1L);
		patch.setCommentId(1L);
		patch.setContent("modified-comment");
		return patch;
	}

	public static QuestionCommentDto.Response getDefaultResponse(boolean isModifiedComment) {
		QuestionCommentDto.Response response = new QuestionCommentDto.Response();
		response.setQuestionId(1L);
		response.setMember(StubData.MockMember.getSingleResponseBody());
		response.setContent(isModifiedComment ? "modified-comment" : "comment");
		response.setCreatedAt(LocalDateTime.now());
		response.setLastModifiedAt(LocalDateTime.now());
		return response;
	}
}
