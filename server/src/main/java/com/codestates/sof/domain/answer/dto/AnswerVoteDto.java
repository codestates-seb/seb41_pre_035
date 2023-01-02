package com.codestates.sof.domain.answer.dto;

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
		@NotNull
		private VoteType voteType;
	}
}
