package com.codestates.sof.domain.answer.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.entity.AnswerVote;
import com.codestates.sof.domain.answer.repository.AnswerVoteRepository;
import com.codestates.sof.domain.member.entity.Member;

@Transactional
@Service
public class AnswerVoteService {
	private final AnswerVoteRepository answerVoteRepository;
	private final AnswerService answerService;

	public AnswerVoteService(AnswerVoteRepository answerVoteRepository,
		AnswerService answerService) {
		this.answerVoteRepository = answerVoteRepository;
		this.answerService = answerService;
	}

	@Transactional
	public AnswerVote updateAnswerVote(AnswerVote answerVote, Member member, long answerId) {
		Answer answer = answerService.findVerifiedAnswer(answerId);

		Optional<AnswerVote> optionalVote = answerVoteRepository.findByMemberAndAnswer(member, answer);
		AnswerVote vote = optionalVote.orElseGet(
			() -> answerVoteRepository.save(new AnswerVote(answer, member, answerVote.getType())));

		vote.modify(answerVote.getType());
		answer.setVoteCount(answerService.getAnswerVoteCount(answer));

		return vote;
	}
}
