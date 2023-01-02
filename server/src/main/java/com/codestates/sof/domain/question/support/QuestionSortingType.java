package com.codestates.sof.domain.question.support;

public enum QuestionSortingType {
	NEWEST, UNACCEPTED, UNANSWERED;

	public static QuestionSortingType getDefaultSortingType() {
		return NEWEST;
	}
}
