package com.codestates.sof.domain.question.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.codestates.sof.domain.member.dto.MemberDto;

import lombok.Getter;
import lombok.Setter;

public class QuestionCommentDto {
	@Getter
	@Setter
	public static class Post {
		@Positive(message = "MemberId must be greater than 0")
		private Long memberId;

		@Positive(message = "QuestionId must be greater than 0")
		private Long questionId;

		@NotBlank(message = "Content must not be null")
		private String content;
	}

	@Getter
	@Setter
	public static class Patch {
		@Positive(message = "Modifier Id must be greater than 0")
		private Long modifierId;

		@Positive(message = "QuestionId must be greater than 0")
		private Long questionId;

		@Positive(message = "CommentId must be greater than 0")
		private Long commentId;

		@NotBlank(message = "Content must not be null")
		private String content;
	}

	@Getter
	@Setter
	public static class Response {
		private MemberDto.Response member;
		private Long questionId;
		private String content;
	}
}
