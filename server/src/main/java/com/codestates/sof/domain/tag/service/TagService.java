package com.codestates.sof.domain.tag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.tag.entity.Tag;
import com.codestates.sof.domain.tag.repository.TagRepository;
import com.codestates.sof.domain.tag.support.TagPageRequest;
import com.codestates.sof.domain.tag.support.TagSortingType;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
	private final TagRepository tagRepository;

	public Tag findBy(String tagName) {
		return findExistsTagBy(tagName);
	}

	public List<Tag> findAllBy(List<String> tagNames) {
		return tagRepository.findAllByNameIn(
			tagNames.stream().map(String::toLowerCase).collect(Collectors.toList())
		);
	}

	public Page<Tag> findAll(TagPageRequest pageRequest) {
		if (pageRequest.getSortType() == TagSortingType.POPULAR)
			return tagRepository.findAllByOrderByTaggedCountDesc(pageRequest.unsorted());

		return tagRepository.findAllByOrderByNameAsc(pageRequest.unsorted());
	}

	public Page<Tag> search(String query, TagPageRequest pageRequest) {
		query = query.toLowerCase();

		if (pageRequest.getSortType() == TagSortingType.POPULAR)
			return tagRepository.findAllByNameLikeOrderByTaggedCountDesc(query, pageRequest.unsorted());

		return tagRepository.findAllByNameLikeOrderByNameAsc(query, pageRequest.unsorted());
	}

	private Tag findExistsTagBy(String tagName) {
		return tagRepository.findByName(tagName.toLowerCase())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_TAG));
	}
}
