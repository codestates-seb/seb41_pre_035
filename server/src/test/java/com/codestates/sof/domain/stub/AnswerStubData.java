package com.codestates.sof.domain.stub;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.codestates.sof.domain.answer.dto.AnswerCommentDto;
import com.codestates.sof.domain.answer.dto.AnswerDto;
import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.entity.AnswerComment;
import com.codestates.sof.domain.member.controller.StubData;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.entity.Question;

public class AnswerStubData {

	public static class MockAnswer {

		public static AnswerDto.Post getAnswerPostDto() {
			AnswerDto.Post post = new AnswerDto.Post();
			post.setMemberId(1L);
			post.setContent("답변");

			return post;
		}

		public static AnswerDto.Patch getAnswerPatchDto() {
			AnswerDto.Patch patch = new AnswerDto.Patch();
			patch.setMemberId(1L);
			patch.setContent("답변");

			return patch;
		}

		public static AnswerDto.Response getAnswerResponseDto() {
			AnswerDto.Response response = new AnswerDto.Response();
			response.setAnswerId(1L);
			response.setQuestionId(1L);
			response.setMemberId(1L);
			response.setContent("답변");
			response.setVoteCount(0);
			response.setIsItWriter(true);
			response.setHasAlreadyVoted(false);
			response.setCreatedAt(LocalDateTime.now());
			response.setLastModifiedAt(LocalDateTime.now());

			return response;
		}

		public static Page<Answer> getPageAnswers() {
			Member member1 = new Member();
			member1.setMemberId(1L);
			Question question1 = new Question();
			question1.setQuestionId(1L);
			Member member2 = new Member();
			member1.setMemberId(2L);
			Question question2 = new Question();
			question1.setQuestionId(2L);

			Answer answer1 = new Answer();
			answer1.setContent("답변1");
			answer1.setMember(member1);
			answer1.setQuestion(question1);

			Answer answer2 = new Answer();
			answer1.setContent("답변2");
			answer1.setMember(member2);
			answer1.setQuestion(question2);

			return new PageImpl<>(List.of(answer1, answer2),
				PageRequest.of(0, 30, Sort.by("answerId").descending()), 2);
		}

		public static List<AnswerDto.Response> getMultiAnswerResponseDto() {
			AnswerDto.Response response1 = new AnswerDto.Response();
			response1.setAnswerId(1L);
			response1.setQuestionId(1L);
			response1.setMemberId(1L);
			response1.setContent("답변1");
			response1.setVoteCount(0);
			response1.setIsItWriter(true);
			response1.setHasAlreadyVoted(false);
			response1.setCreatedAt(LocalDateTime.now());
			response1.setLastModifiedAt(LocalDateTime.now());

			AnswerDto.Response response2 = new AnswerDto.Response();
			response2.setAnswerId(2L);
			response2.setQuestionId(2L);
			response2.setMemberId(2L);
			response2.setContent("답변2");
			response2.setVoteCount(0);
			response2.setIsItWriter(true);
			response2.setHasAlreadyVoted(false);
			response2.setCreatedAt(LocalDateTime.now());
			response2.setLastModifiedAt(LocalDateTime.now());

			return List.of(response1, response2);
		}
	}

	public static class MockAnswerComment {

		public static AnswerCommentDto.Post getAnswerCommentPostDto() {
			AnswerCommentDto.Post post = new AnswerCommentDto.Post();
			post.setMemberId(1L);
			post.setContent("댓글");

			return post;
		}

		public static AnswerCommentDto.Patch getAnswerCommentPatchDto() {
			AnswerCommentDto.Patch patch = new AnswerCommentDto.Patch();
			patch.setMemberId(1L);
			patch.setContent("댓글");

			return patch;
		}

		public static AnswerCommentDto.Response getAnswerCommentResponseDto() {
			AnswerCommentDto.Response response = new AnswerCommentDto.Response();
			response.setCommentId(1L);
			response.setAnswerId(1L);
			response.setMember(StubData.MockMember.getSingleResponseBody());
			response.setContent("댓글");
			response.setCreatedAt(LocalDateTime.now());
			response.setLastModifiedAt(LocalDateTime.now());

			return response;
		}

		public static Page<AnswerComment> getPageAnswerComments() {
			AnswerComment comment1 = new AnswerComment("댓글1");
			AnswerComment comment2 = new AnswerComment("댓글2");

			return new PageImpl<>(List.of(comment1, comment2),
				PageRequest.of(0, 7, Sort.by("answerId").descending()), 2);
		}

		public static List<AnswerCommentDto.Response> getMultiAnswerCommentResponseDto() {
			AnswerCommentDto.Response response1 = new AnswerCommentDto.Response();
			response1.setCommentId(1L);
			response1.setAnswerId(1L);
			response1.setMember(StubData.MockMember.getSingleResponseBody());
			response1.setContent("댓글1");
			response1.setCreatedAt(LocalDateTime.now());
			response1.setLastModifiedAt(LocalDateTime.now());

			AnswerCommentDto.Response response2 = new AnswerCommentDto.Response();
			response2.setCommentId(2L);
			response2.setAnswerId(2L);
			response2.setMember(StubData.MockMember.getSingleResponseBody());
			response2.setContent("댓글2");
			response2.setCreatedAt(LocalDateTime.now());
			response2.setLastModifiedAt(LocalDateTime.now());

			return List.of(response1, response2);
		}
	}
}
