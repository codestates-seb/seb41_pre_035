package com.codestates.sof.domain.question.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.sof.domain.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	Optional<Question> findByQuestionId(Long questionId);
	Optional<Question> findByWriterIdAndQuestionId(Long writerId, Long questionId);
}
