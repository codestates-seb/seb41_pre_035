package com.codestates.sof.domain.question.support;

import org.springframework.core.MethodParameter;

import com.codestates.sof.global.config.support.PageableHandlerMethodargumentResolver;

public class QuestionPageableArgumentResolver extends PageableHandlerMethodargumentResolver<QuestionPageRequest> {
	public QuestionPageableArgumentResolver(int defaultPageSize, int defaultSize) {
		super(defaultPageSize, defaultSize);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return QuestionPageRequest.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	protected QuestionPageRequest getPageRequest(int page, int size, String sortString) {
		return new QuestionPageRequest(page - 1, size, getSortingType(sortString));
	}

	private QuestionSortingType getSortingType(String sortString) {
		try {
			return QuestionSortingType.valueOf(sortString);
		} catch (Exception ignore) {
			return QuestionSortingType.getDefaultSortingType();
		}
	}
}
