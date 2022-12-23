package com.codestates.sof.domain.tag.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

public class TagDto {
	@Setter
	@Getter
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	public static class Response {
		// 특정 태그가 태깅된 질문들은 질문 ResponseDto로 응답되므로 뺌
		private Long tagId;
		private String name;
		private String description;
		private Long taggedCount;
	}
}
