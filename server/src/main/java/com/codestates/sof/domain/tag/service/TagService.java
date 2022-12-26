package com.codestates.sof.domain.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.tag.entity.Tag;
import com.codestates.sof.domain.tag.repository.TagRepository;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
	private final TagRepository tagRepository;

	public Tag create(Tag tag) {
		tag.beforeSave();
		return tagRepository.save(tag);
	}

	public Tag findBy(String tagName) {
		return findExistsTagBy(tagName);
	}

	public List<Tag> findAllBy(List<String> tagNames) {
		return tagRepository.findAllByNameIn(tagNames);
	}

	private Tag findExistsTagBy(String tagName) {
		return tagRepository.findByName(tagName)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_TAG));
	}
}
