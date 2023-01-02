package com.codestates.sof.domain.answer.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AnswerDto {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post {
		@Min(value = 0, message = "QuestionId must be equal and more than 0")
		private Long questionId;

		@Min(value = 0, message = "MemberId must be equal and more than 0")
		private Long memberId;

		@NotBlank(message = "content must not be null")
		private String content;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Patch {
		@Min(value = 0, message = "MemberId must be equal and more than 0")
		private Long memberId;

		@NotBlank
		private String content;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long answerId;
		private Long questionId;
		private Long memberId;
		private String content;
		private int voteCount;
		private boolean isAccepted;
		private boolean isItWriter;
		private boolean hasAlreadyVoted;
		private LocalDateTime createdAt;
		private LocalDateTime lastModifiedAt;

		public boolean getIsItWriter() {
			return isItWriter;
		}

		public void setIsItWriter(boolean isItWriter) {
			this.isItWriter = isItWriter;
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Acceptance {
		@Positive(message = "Id of the answer to be accepted must be greater than 0")
		private long answerId;
	}
}
