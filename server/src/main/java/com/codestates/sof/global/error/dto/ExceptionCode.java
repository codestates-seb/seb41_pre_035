package com.codestates.sof.global.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
	// 403
	NO_PERMISSION_EDITING_QUESTION(40301, "Only a writer can edit this question."),

	// 404
	NOT_FOUND_MEMBER(40401, "Member not found"),
	NOT_FOUND_QUESTION(40402, "Question not found"),
	NOT_FOUND_ANSWER(40403, "Answer not found"),

	// 409
	MEMBER_EXISTS(40901, "Member already exist");

	// 5xx

	int status;
	String message;
}
