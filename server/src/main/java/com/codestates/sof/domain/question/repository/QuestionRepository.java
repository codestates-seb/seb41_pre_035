package com.codestates.sof.domain.question.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.tag.entity.Tag;

// https://junyharang.tistory.com/173
// https://jojoldu.tistory.com/516
// limit: https://donnaknew.tistory.com/5

public interface QuestionRepository extends JpaRepository<Question, Long> {
	Optional<Question> findByQuestionId(Long questionId);

	Optional<Question> findByMemberAndQuestionId(Member member, Long questionId);

	// NEWEST
	Page<Question> findAllByOrderByCreatedAtDesc(Pageable pageable);

	@Query("select q from Question q inner join QuestionTag qt where qt.tag = :tag")
	Page<Question> findAllByTag(Tag tag, Pageable pageable);

	@Query("select q from Question q where q.title like concat('%', :query, '%') or q.content like concat('%', :query, '%')")
	Page<Question> findAllByTitleOrContentLike(String query, Pageable pageable);

	// UNACCEPTED
	Page<Question> findAllByHasAcceptedAnswerIsFalse(Pageable pageable);

	@Query("select q from Question q "
		+ "where q.hasAcceptedAnswer = false and (q.title like concat('%', :query, '%') or q.content like concat('%', :query, '%'))")
	Page<Question> findAllByHasAcceptedAnswerIsFalseAndTitleOrContent(String query, Pageable pageable);

	@Query("select q from Question q inner join QuestionTag qt where qt.tag = :tag and q.hasAcceptedAnswer = false")
	Page<Question> findAllByTagAndHasAcceptedAnswerIsFalse(Tag tag, Pageable pageable);

	// UNANSWERED
	// @Query("select q from Question q left join q.answers a where a.answerId is null")
	@Query("select q from Question q where not exists (select 1 from Answer a where a.question = q)")
	Page<Question> findAllByAnswersEmpty(Pageable pageable);

	@Query("select q from Question q "
		+ "where not exists (select 1 from Answer a where a.question = q) "
		+ "and (q.title like concat('%', :query, '%') or q.content like concat('%', :query, '%'))")
	Page<Question> findAllByAnswersIsEmptyAndTitleOrContentLike(String query, Pageable pageable);

	@Query("select q from Question q inner join QuestionTag qt where qt.tag = :tag and q.hasAcceptedAnswer = false and not exists (select 1 from Answer a where a.question = q)")
	Page<Question> findAllByTagAndAnswersEmpty(Tag tag, Pageable pageable);
}
