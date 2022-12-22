package com.codestates.sof.domain.question.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.codestates.sof.domain.question.entity.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {
	Optional<Question> findByQuestionId(Long questionId);
}
