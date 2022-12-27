package com.codestates.sof.domain.question.support;

public enum QuestionSortingType {
	NEWEST, UNADOPTED, UNANSWERED;

	public static QuestionSortingType getDefaultSortingType() {
		return NEWEST;
	}
}
