package com.codestates.sof.domain.question.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class QuestionDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post {
		@Min(value = 0, message = "WriterId must be equal and more than 0")
		private Long writerId;

		@NotBlank(message = "title must not be null")
		private String title;

		@NotBlank(message = "content must not be null")
		private String content;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long questionId;
		private Long writerId;
		private String title;
		private String content;
		private int viewCount;
		private int voteCount;
		private Boolean isItWriter;
		private Boolean hasAlreadyVoted;
		private LocalDateTime createdAt;
		private LocalDateTime lastModifiedAt;

		public boolean getIsItWriter() {
			return isItWriter;
		}

		public void setIsItWriter(boolean isItWriter) {
			this.isItWriter = isItWriter;
		}
	}
}
