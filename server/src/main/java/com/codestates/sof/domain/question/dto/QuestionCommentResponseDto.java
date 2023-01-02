package com.codestates.sof.domain.question.dto;

import java.time.LocalDateTime;

import com.codestates.sof.domain.member.dto.MemberDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class QuestionCommentResponseDto {
	@Getter
	@Setter
	@ToString
	public static class Response {
		private MemberDto.Response member;
		private Long questionId;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime lastModifiedAt;
	}

	@Getter
	@Setter
	public static class SimpleResponse {
		private Long questionId;
		private Long memberId;
		private String memberName;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime lastModifiedAt;
	}
}
