package com.codestates.sof.domain.question.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.codestates.sof.domain.answer.dto.AnswerDto;
import com.codestates.sof.domain.member.dto.MemberDto;
import com.codestates.sof.domain.tag.dto.TagDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class QuestionDto {
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post {
		@Positive(message = "WriterId must be greater than 0")
		private Long writerId;

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
		@Positive(message = "MemberId must be greater than 0")
		private Long memberId;

		@NotBlank(message = "Title must not be null")
		private String title;

		@NotBlank(message = "Content must not be null")
		private String content;

		private List<String> tags;
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
		private Boolean isItWriter;
		private Boolean hasAlreadyVoted;
		private LocalDateTime createdAt;
		private LocalDateTime lastModifiedAt;
		private List<TagDto.Response> tags;
		private List<AnswerDto.Response> answers;

		public boolean getIsItWriter() {
			return isItWriter;
		}

		public void setIsItWriter(boolean isItWriter) {
			this.isItWriter = isItWriter;
		}
	}
}
