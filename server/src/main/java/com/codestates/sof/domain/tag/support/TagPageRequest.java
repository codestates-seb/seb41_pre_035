package com.codestates.sof.domain.tag.support;

import com.codestates.sof.global.config.support.CustomPageRequest;

public class TagPageRequest extends CustomPageRequest<TagSortingType> {
	public TagPageRequest(int page, int size, TagSortingType sortType) {
		super(page, size, sortType);
	}
}
