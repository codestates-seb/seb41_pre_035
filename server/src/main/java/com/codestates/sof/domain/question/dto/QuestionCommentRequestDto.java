package com.codestates.sof.domain.question.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

public class QuestionCommentRequestDto {
	@Getter
	@Setter
	public static class Post {
		@NotBlank(message = "Content must not be null")
		private String content;
	}

	@Getter
	@Setter
	public static class Patch {
		@NotBlank(message = "Content must not be null")
		private String content;
	}

}
