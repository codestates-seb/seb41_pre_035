package com.codestates.sof.domain.answer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.repository.AnswerRepository;

@Transactional
@Service
public class AnswerService {
	private final AnswerRepository answerRepository;

	public AnswerService(AnswerRepository answerRepository) {
		this.answerRepository = answerRepository;
	}

	public Answer createAnswer(Answer answer) {
		// 필요하다면 verify 추가
		Answer savedAnswer = answerRepository.save(answer);

		return savedAnswer;
	}
}
