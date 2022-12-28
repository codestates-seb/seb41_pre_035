package com.codestates.sof.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LoginDto {
	@Getter
	public static class Post {
		private String username;
		private String password;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private long memberId;
		private String email;
		private String name;
		private String avatarUrl;
	}
}