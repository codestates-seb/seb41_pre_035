package com.codestates.sof.global.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
	// 400
	INVALID_TOKEN(40001, "Invalid token"),

	// 403
	NO_PERMISSION_EDITING_QUESTION(40301, "Only a writer can edit this question."),
	NO_PERMISSION_EDITING_COMMENT(40302, "Only a writer can edit this comment."),
	NO_PERMISSION_EDITING_ANSWER(40303, "Only a writer can edit this answer."),

	// 404
	NOT_FOUND_MEMBER(40401, "Member not found"),
	NOT_FOUND_QUESTION(40402, "Question not found"),
	NOT_FOUND_ANSWER(40403, "Answer not found"),
	NOT_FOUND_TAG(40404, "Tag not found"),
	NOT_FOUND_TOKEN(40405, "Token not found"),
	NOT_FOUND_COMMENT(40406, "Comment not found"),

	// 409
	MEMBER_EXISTS(40901, "Member already exist"),
	EMAIL_VERIFICATION_REQUIRED(40902, "Member already exist, and email verification is required"),

	// 5xx
	EMAIL_SEND_FAILURE(50301, "Verification email error. Please try again later.");

	int status;
	String message;
}
