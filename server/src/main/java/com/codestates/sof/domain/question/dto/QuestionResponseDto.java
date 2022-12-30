package com.codestates.sof.domain.question.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.codestates.sof.domain.answer.dto.AnswerDto;
import com.codestates.sof.domain.common.VoteType;
import com.codestates.sof.domain.member.dto.MemberDto;
import com.codestates.sof.domain.tag.dto.TagDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class QuestionResponseDto {
	@Setter
	@Getter
	@NoArgsConstructor
	public static class SimpleResponse {
		private Long questionId;
		private Long writerId;
		private String writerName;
		private String title;
		private String content;
		private int answerCount;
		private int viewCount;
		private int voteCount;
		private boolean hasAlreadyVoted;
		private boolean hasAcceptedAnswer;
		private LocalDateTime createdAt;
		private LocalDateTime lastModifiedAt;
		private List<TagDto.Response> tags;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long questionId;
		private MemberDto.Response writer;
		private String title;
		private String content;
		private int viewCount;
		private int voteCount;
		private boolean isBookmarked;
		private boolean isItWriter;
		private VoteType voteType;
		private LocalDateTime createdAt;
		private LocalDateTime lastModifiedAt;
		private List<TagDto.Response> tags;
		private List<AnswerDto.Response> answers;

		public void setIsItWriter(boolean isItWriter) {
			this.isItWriter = isItWriter;
		}
	}
}
