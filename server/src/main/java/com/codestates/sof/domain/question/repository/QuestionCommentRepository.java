package com.codestates.sof.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.sof.domain.question.entity.QuestionComment;

public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {
}
