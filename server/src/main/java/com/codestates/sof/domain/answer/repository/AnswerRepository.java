package com.codestates.sof.domain.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.sof.domain.answer.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
