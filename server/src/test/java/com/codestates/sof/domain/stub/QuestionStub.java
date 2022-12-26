package com.codestates.sof.domain.stub;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import com.codestates.sof.domain.member.controller.StubData;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.dto.QuestionDto;
import com.codestates.sof.domain.question.entity.Question;

public class QuestionStub {
	public enum Type {
		DEFAULT(QuestionStub::getDefaultQuestion),
		POST(QuestionStub::getPostRequest),
		PATCH(QuestionStub::getPathRequest),
		RESPONSE(QuestionStub::getResponse);

		private final Supplier<Object> data;

		Type(Supplier<Object> data) {
			this.data = data;
		}

		public Object create() {
			return data.get();
		}
	}

	private static QuestionDto.Post getPostRequest() {
		QuestionDto.Post post = new QuestionDto.Post();
		post.setWriterId(1L);
		post.setTitle("title");
		post.setContent("content");
		post.setTags(List.of("java", "javascript", "python"));
		return post;
	}

	private static QuestionDto.Patch getPathRequest() {
		QuestionDto.Patch patch = new QuestionDto.Patch();
		patch.setMemberId(1L);
		patch.setTitle("modified-title");
		patch.setContent("modified-content");
		patch.setTags(List.of("java", "javascript"));
		return patch;
	}

	private static QuestionDto.Response getResponse() {
		QuestionDto.Response response = new QuestionDto.Response();
		response.setQuestionId(1L);
		response.setWriter(StubData.MockMember.getSingleResponseBody());
		response.setTitle("title");
		response.setContent("content");
		response.setViewCount(0);
		response.setVoteCount(0);
		response.setIsItWriter(false);
		response.setHasAlreadyVoted(false);
		response.setCreatedAt(LocalDateTime.now());
		response.setLastModifiedAt(LocalDateTime.now());
		response.setTags(Collections.emptyList());
		response.setAnswers(Collections.emptyList());
		return response;
	}

	private static Question getDefaultQuestion() {
		final Member member = new Member();
		member.setMemberId(1L);

		return Question.Builder.aQuestion()
			.questionId(1L)
			.writer(member)
			.title("title")
			.content("content")
			.viewCount(0)
			.voteCount(0)
			.build();
	}
}
