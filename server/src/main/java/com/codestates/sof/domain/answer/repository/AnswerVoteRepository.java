package com.codestates.sof.domain.answer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.entity.AnswerVote;
import com.codestates.sof.domain.member.entity.Member;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
	Optional<AnswerVote> findByMemberAndAnswer(Member member, Answer answer);
}
