package com.codestates.sof.domain.tag.support;

import org.springframework.core.MethodParameter;

import com.codestates.sof.global.config.support.PageableHandlerMethodargumentResolver;

public class TagPageableArgumentResolver extends PageableHandlerMethodargumentResolver<TagPageRequest> {
	public TagPageableArgumentResolver(int defaultPageSize, int defaultSize) {
		super(defaultPageSize, defaultSize);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return TagPageRequest.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	protected TagPageRequest getPageRequest(int page, int size, String sortString) {
		return new TagPageRequest(page - 1, size, getSortingType(sortString));
	}

	private TagSortingType getSortingType(String sortString) {
		try {
			return TagSortingType.valueOf(sortString.toUpperCase());
		} catch (Exception ignore) {
			return TagSortingType.getDefaultSortingType();
		}
	}
}
