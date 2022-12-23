package com.codestates.sof.domain.tag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.sof.domain.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByName(String tagName);

	List<Tag> findAllByName(List<String> tagNames);
}
