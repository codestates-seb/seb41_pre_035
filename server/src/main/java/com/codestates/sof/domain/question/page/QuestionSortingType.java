package com.codestates.sof.domain.question.page;

public enum QuestionSortingType {
	NEWEST, UNADOPTED, UNANSWERED;

	public String getType() {
		return name();
	}

	public static QuestionSortingType getDefaultSortingType() {
		return NEWEST;
	}
}
