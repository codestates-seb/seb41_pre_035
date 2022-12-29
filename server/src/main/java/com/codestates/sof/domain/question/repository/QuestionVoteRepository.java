package com.codestates.sof.domain.question.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.sof.domain.common.VoteType;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionVote;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {
	Optional<QuestionVote> findByMemberAndQuestion(Member member, Question question);

	boolean existsByMemberAndQuestion(Member member, Question question);

	@Query(nativeQuery = true, value =
		"select qv.vote_type from Question_Vote qv "
			+ "inner join Question q on qv.question_id = q.question_id "
			+ "where q.question_id = ?1")
	List<VoteType> getTypesByQuestion(long questionId);
}
