package com.codestates.sof.domain.question.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

public class QuestionCommentDto {
	@Getter
	@Setter
	public static class Post {
		// TODO (Auth)
		@Positive(message = "MemberId must be greater than 0")
		private Long memberId;

		@NotBlank(message = "Content must not be null")
		private String content;
	}

	@Getter
	@Setter
	public static class Patch {
		// TODO (Auth)
		@Positive(message = "Modifier Id must be greater than 0")
		private Long modifierId;

		@NotBlank(message = "Content must not be null")
		private String content;
	}

}
