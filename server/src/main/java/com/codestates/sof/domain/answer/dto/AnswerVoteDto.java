package com.codestates.sof.domain.answer.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.codestates.sof.domain.common.VoteType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AnswerVoteDto {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Patch {
		@Min(value = 0, message = "MemberId must be equal and more than 0")
		private Long memberId;

		@NotNull
		private VoteType voteType;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long answerVoteId;
		private Long memberId;
		private Long answerId;
		private VoteType voteType;
		private LocalDateTime createdAt;
		private LocalDateTime lastModifiedAt;
	}
}
