package com.codestates.sof.domain.answer.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.codestates.sof.domain.member.dto.MemberDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AnswerCommentDto {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post {
		@Min(value = 1, message = "MemberId must be equal and more than 1")
		private Long memberId;

		@NotBlank(message = "Content must not be null")
		private String content;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Patch {
		@Min(value = 1, message = "MemberId must be equal and more than 1")
		private Long memberId;

		@NotBlank(message = "Content must not be null")
		private String content;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private Long commentId;
		private Long answerId;
		private MemberDto.Response member;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime lastModifiedAt;
	}
}
