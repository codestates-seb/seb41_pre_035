package com.codestates.sof.global.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
	// 403

	// 404
	NOT_FOUND_MEMBER(40401, "Member not found"),
	NOT_FOUND_QUESTION(40402, "Question not found"),
	NOT_FOUND_ANSWER(40403, "Answer not found");

	// 5xx

	int status;
	String message;
}
