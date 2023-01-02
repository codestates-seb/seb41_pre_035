package com.codestates.sof.domain.answer.support;

public enum AnswerSortingType {
	NEWEST, OLDEST, HIGH_SCORE;

	public static AnswerSortingType getDefaultSortingType() {
		return HIGH_SCORE;
	}
}
