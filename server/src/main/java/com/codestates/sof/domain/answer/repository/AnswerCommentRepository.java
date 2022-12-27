package com.codestates.sof.domain.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.sof.domain.answer.entity.AnswerComment;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Long> {
}
