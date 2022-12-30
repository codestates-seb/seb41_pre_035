package com.codestates.sof.domain.tag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.sof.domain.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByName(String tagName);

	List<Tag> findAllByNameIn(List<String> tagNames);

	Page<Tag> findAllByOrderByNameAsc(Pageable pageable);
	Page<Tag> findAllByNameLikeOrderByNameAsc(String query, Pageable pageable);

	Page<Tag> findAllByOrderByTaggedCountDesc(Pageable pageable);
	Page<Tag> findAllByNameLikeOrderByTaggedCountDesc(String query, Pageable pageable);
}
