package com.codestates.sof.domain.question.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.entity.Bookmark;
import com.codestates.sof.domain.question.entity.Question;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	Optional<Bookmark> findByMemberAndQuestion(Member member, Question question);

	Page<Bookmark> findAllByMemberOrderByCreatedAtDesc(Member member, Pageable pageable);
}
