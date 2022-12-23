package com.codestates.sof.domain.tag.dto;

public class TagDto {
	// 특정 태그가 태깅된 질문들은 질문 ResponseDto로 응답되므로 뺌
	public static class Response {
		private Long tagId;
		private String name;
		private String description;
		private Long taggedCount;
	}
}
