package com.codestates.sof.domain.question.dto;

import com.codestates.sof.domain.common.VoteType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

public class QuestionVoteDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post {
		@NonNull
		private VoteType voteType;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Patch {
		@NonNull
		private VoteType voteType;
	}
}
