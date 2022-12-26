package com.codestates.sof.domain.question.page;

public class QuestionPageRequest {
	private final int page;
	private final int size;
	private final QuestionSortingType sortType;

	public QuestionPageRequest(int page, int size) {
		this(page, size, QuestionSortingType.getDefaultSortingType());
	}

	public QuestionPageRequest(int page, int size, QuestionSortingType sortType) {
		this.page = page;
		this.size = size;
		this.sortType = sortType;
	}

	public int getSize() {
		return size;
	}

	public int getPage() {
		return page;
	}

	public QuestionSortingType getType() {
		return sortType;
	}
}
