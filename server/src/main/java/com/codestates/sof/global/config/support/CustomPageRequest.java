package com.codestates.sof.global.config.support;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.Getter;

@Getter
public abstract class CustomPageRequest<E extends Enum<?>> {
	private final int page;
	private final int size;
	private final E sortType;

	public CustomPageRequest(int page, int size, E sortType) {
		this.page = page;
		this.size = size;
		this.sortType = sortType;
	}

	public PageRequest of(Sort sort) {
		return PageRequest.of(page, size, sort);
	}

	public PageRequest of() {
		return of(Sort.by("createdAt").descending());
	}
}
