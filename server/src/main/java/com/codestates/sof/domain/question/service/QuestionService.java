package com.codestates.sof.domain.question.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.repository.QuestionRepository;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {
	private final QuestionRepository questionRepository;

	@Transactional
	public Question write(Question question) {
		question = save(question);
		question.postWrote();
		return question;
	}

	@Transactional
	public Question findById(Long questionId) {
		Question question = findExistsQuestion(questionId);
		question.afterFound();
		return question;
	}

	private Question findExistsQuestion(Long questionId) {
		return questionRepository.findByQuestionId(questionId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_QUESTION));
	}

	private Question save(Question question) {
		return questionRepository.save(question);
	}
}
