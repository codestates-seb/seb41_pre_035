package com.codestates.sof.domain.answer.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.repository.AnswerRepository;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.service.MemberService;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.service.QuestionService;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

@Transactional
@Service
public class AnswerService {
	private final AnswerRepository answerRepository;
	private final MemberService memberService;
	private final QuestionService questionService;

	public AnswerService(AnswerRepository answerRepository, MemberService memberService,
		QuestionService questionService) {
		this.answerRepository = answerRepository;
		this.memberService = memberService;
		this.questionService = questionService;
	}

	public Answer createAnswer(Answer answer) {
		Member findMember = memberService.findMember(answer.getMember().getMemberId());
		answer.setMember(findMember);

		Question findQuestion = questionService.findById(answer.getQuestion().getQuestionId());
		answer.setQuestion(findQuestion);

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

	public Page<Answer> findAnswers(int page, int size) {
		return answerRepository.findAll(PageRequest.of(page, size,
			Sort.by("answerId").descending()));
	}

	public void deleteAnswer(long answerId, Member member) {
		Answer findAnswer = findVerifiedAnswer(answerId);
		verifyExistMember(findAnswer, member.getMemberId());

		answerRepository.delete(findAnswer);
	}

	@Transactional(readOnly = true)
	public Answer findVerifiedAnswer(long answerId) {
		Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

		Answer findAnswer =
			optionalAnswer.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ANSWER));

		return findAnswer;
	}

	private void verifyExistMember(Answer answer, long memberId) {
		long memberIdByAnswer = answer.getMember().getMemberId();

		if (memberIdByAnswer == memberId) {
			return;
		}

		throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_EDITING_ANSWER);
	}
}
