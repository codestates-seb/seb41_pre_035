package com.codestates.sof.domain.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionComment;

public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {
	Page<QuestionComment> findAllByQuestion(Question question, Pageable pageable);
}
