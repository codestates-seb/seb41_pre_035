package com.codestates.sof.domain.question.page;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.Setter;

@Setter
public class QuestionPageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	private static final int DEFAULT_PAGE_SIZE = 1;
	private static final int DEFAULT_SIZE = 5;
	private String pageParameterName;
	private String sizeParameterName;
	private String sortParameterName;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return QuestionPageRequest.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		String pageString = webRequest.getParameter(pageParameterName);
		String sizeString = webRequest.getParameter(sizeParameterName);
		String sortString = webRequest.getParameter(sortParameterName);

		int page = Math.max(1, parseInt(pageString, DEFAULT_PAGE_SIZE));
		int size = parseInt(sizeString, DEFAULT_SIZE);

		return new QuestionPageRequest(page, size, getSortingType(sortString));
	}

	private QuestionSortingType getSortingType(String sortString) {
		try {
			return QuestionSortingType.valueOf(sortString);
		} catch (Exception ignore) {
			return QuestionSortingType.getDefaultSortingType();
		}
	}

	private Integer parseInt(String strInt, int defaultValue) {
		try {
			return Integer.parseInt(strInt);
		} catch (Exception ignore) {
			return defaultValue;
		}
	}
}
