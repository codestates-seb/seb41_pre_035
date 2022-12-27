package com.codestates.sof.domain.question.support;

import com.codestates.sof.global.config.support.CustomPageRequest;

public class QuestionPageRequest extends CustomPageRequest<QuestionSortingType> {
	public QuestionPageRequest(int page, int size, QuestionSortingType sortType) {
		super(page, size, sortType);
	}
}
