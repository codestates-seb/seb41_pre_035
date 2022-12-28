package com.codestates.sof.domain.question.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class QuestionRequestDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post {
		@NotBlank(message = "Title must not be null")
		private String title;

		@NotBlank(message = "Content must not be null")
		private String content;

		private List<String> tags;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Patch {
		@NotBlank(message = "Title must not be null")
		private String title;

		@NotBlank(message = "Content must not be null")
		private String content;

		private List<String> tags;
	}

}
