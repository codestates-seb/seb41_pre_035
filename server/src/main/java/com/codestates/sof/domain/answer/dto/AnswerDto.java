package com.codestates.sof.domain.answer.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AnswerDto {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Post {

		private String content;
	}

	@Getter
	@AllArgsConstructor
	public static class Patch {
		private long answerId;

		@NotBlank
		private String content;
	}

	@Getter
	@AllArgsConstructor
	public static class Response {
		private long answerId;
		private String content;
		private int voteCount;
	}
}
