package com.codestates.sof.domain.question.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionVote;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {
	Optional<QuestionVote> findByMemberAndQuestion(Member member, Question question);
	boolean existsByMemberAndQuestion(Member member, Question question);
}
