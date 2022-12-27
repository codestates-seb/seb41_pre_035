package com.codestates.sof.domain.tag.support;

public enum TagSortingType {
	POPULAR, NAME, NEWEST;

	public static TagSortingType getDefaultSortingType() {
		return POPULAR;
	}
}
