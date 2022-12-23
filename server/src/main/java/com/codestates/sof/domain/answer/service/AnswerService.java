package com.codestates.sof.domain.answer.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.repository.AnswerRepository;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

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

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Answer updateAnswer(Answer answer) {
		// Update는 본인만 가능하므로 작성자 본인 확인 verify 추가
		Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());

		Optional.ofNullable(answer.getContent())
			.ifPresent(findAnswer::setContent);

		return answerRepository.save(findAnswer);
	}

	@Transactional(readOnly = true)
	public Answer findVerifiedAnswer(long answerId) {
		Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

		Answer findAnswer =
			optionalAnswer.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ANSWER));

		return findAnswer;
	}
}
