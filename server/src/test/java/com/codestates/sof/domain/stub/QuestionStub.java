package com.codestates.sof.domain.stub;

import java.time.LocalDateTime;
import java.util.List;

import com.codestates.sof.domain.member.controller.StubData;
import com.codestates.sof.domain.member.dto.MemberDto;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.dto.QuestionRequestDto;
import com.codestates.sof.domain.question.dto.QuestionResponseDto;
import com.codestates.sof.domain.question.entity.Question;

public class QuestionStub {
	public static Question getDefaultQuestion() {
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

	public static QuestionRequestDto.Post getPostRequest() {
		QuestionRequestDto.Post post = new QuestionRequestDto.Post();
		post.setTitle("title");
		post.setContent("content");
		post.setTags(List.of("java", "javascript", "python"));
		return post;
	}

	public static QuestionRequestDto.Patch getPatchRequest() {
		QuestionRequestDto.Patch patch = new QuestionRequestDto.Patch();
		patch.setTitle("modified-title");
		patch.setContent("modified-content");
		patch.setTags(List.of("java", "javascript"));
		return patch;
	}

	public static QuestionResponseDto.Response getSingleResponse() {
		MemberDto.Response member = StubData.MockMember.getSingleResponseBody();
		member.setProfile(null);
		QuestionResponseDto.Response response = new QuestionResponseDto.Response();
		response.setQuestionId(1L);
		response.setWriter(member);
		response.setTitle("title");
		response.setContent("content");
		response.setViewCount(0);
		response.setVoteCount(0);
		response.setIsItWriter(false);
		response.setHasAlreadyVoted(false);
		response.setCreatedAt(LocalDateTime.now());
		response.setLastModifiedAt(LocalDateTime.now());
		response.setTags(TagStub.simpleResponses());
		response.setAnswers(List.of(AnswerStubData.MockAnswer.getAnswerResponseDto()));
		return response;
	}

	public static QuestionResponseDto.SimpleResponse getSimpleResponse() {
		MemberDto.Response member = StubData.MockMember.getSingleResponseBody();
		member.setProfile(null);
		QuestionResponseDto.SimpleResponse response = new QuestionResponseDto.SimpleResponse();
		response.setQuestionId(1L);
		response.setWriterId(member.getMemberId());
		response.setWriterName(member.getName());
		response.setTitle("title");
		response.setContent("content");
		response.setVoteCount(0);
		response.setViewCount(0);
		response.setAnswerCount(1);
		response.setCreatedAt(LocalDateTime.now());
		response.setLastModifiedAt(LocalDateTime.now());
		response.setTags(TagStub.simpleResponses());
		return response;
	}
}
