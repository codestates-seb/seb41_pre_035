package com.codestates.sof.domain.member.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {
	@Getter
	@Setter
	public static class Post{
		private String email;
		private  String password;
		private String name;

	}

	@Getter
	@AllArgsConstructor
	public static class Response {
		private long memberId;
		private String email;
		private String name;
		private boolean verificationFlag;
		private LocalDateTime createdAt;
	}
}