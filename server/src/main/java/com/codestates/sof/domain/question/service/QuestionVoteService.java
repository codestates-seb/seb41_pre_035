package com.codestates.sof.domain.question.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.common.VoteType;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionVote;
import com.codestates.sof.domain.question.repository.QuestionVoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionVoteService {
	private final QuestionVoteRepository voteRepository;
	private final QuestionService questionService;

	@Transactional
	public int update(Member member, long questionId, VoteType voteType) {
		Question question = questionService.findById(questionId);

		if (voteType == VoteType.NONE)
			return delete(member, question);

		QuestionVote vote = voteRepository.findByMemberAndQuestion(member, question)
			.orElseGet(() -> voteRepository.save(new QuestionVote(question, member, voteType)));

		vote.modify(voteType);

		return question.getVoteCount();
	}

	private int delete(Member member, Question question) {
		voteRepository.findByMemberAndQuestion(member, question)
			.ifPresent(voteRepository::delete);

		return question.getVoteCount();
	}
}
