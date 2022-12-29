package com.codestates.sof.domain.question.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.common.VoteType;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionVote;
import com.codestates.sof.domain.question.repository.QuestionRepository;
import com.codestates.sof.domain.question.repository.QuestionVoteRepository;

@SpringBootTest
@ActiveProfiles("local")
@DisplayName("질문 투표 기능 테스트")
@TestMethodOrder(MethodOrderer.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionVoteServiceTest {
	@Autowired
	QuestionVoteService voteService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	QuestionVoteRepository voteRepository;

	Member member;
	Question question;

	@BeforeAll
	@Transactional
	void beforeAll() {
		removeAll();
		member = new Member();
		member.setName("name");
		member.setEmail("email");
		member.setEncryptedPassword("password");
		member = memberRepository.save(member);

		question = new Question(member, "title", "content");
		question = questionRepository.save(question);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	void removeAll() {
		voteRepository.deleteAll();
		questionRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@AfterEach
	void afterEach() {
		voteRepository.deleteAll();
	}

	@Test
	void NONE이_아닌_타입으로_투표한다() {
		// given
		Member member = memberRepository.findByEmail(this.member.getEmail()).get();
		Question question = questionRepository.findById(this.question.getQuestionId()).get();

		// when
		voteService.update(member, question.getQuestionId(), VoteType.UP);

		// then
		Optional<QuestionVote> optVote = voteRepository.findByMemberAndQuestion(member, question);
		assertTrue(optVote.isPresent());

		QuestionVote vote = optVote.get();
		assertEquals(VoteType.UP, vote.getType());
		assertEquals(question.getQuestionId(), vote.getQuestion().getQuestionId());
	}

	@Test
	void NONE타입으로_투표하면_무시한다() {
		// given
		Member member = memberRepository.findByEmail(this.member.getEmail()).get();
		Question question = questionRepository.findById(this.question.getQuestionId()).get();

		// when
		voteService.update(member, question.getQuestionId(), VoteType.NONE);

		// then
		assertFalse(voteRepository.existsByMemberAndQuestion(member, question));
	}

	@Test
	void NONE이_아닌_타입으로_변경한다() {
		// given
		Member member = memberRepository.findByEmail(this.member.getEmail()).get();
		Question question = questionRepository.findById(this.question.getQuestionId()).get();
		voteService.update(member, question.getQuestionId(), VoteType.UP);

		// when
		voteService.update(member, question.getQuestionId(), VoteType.DOWN);

		// then
		assertTrue(voteRepository.existsByMemberAndQuestion(member, question));
		assertEquals(VoteType.DOWN, voteRepository.findByMemberAndQuestion(member, question).get().getType());
	}

	@Test
	void NONE타입으로_변경하면_삭제된다() {
		// given
		Member member = memberRepository.findByEmail(this.member.getEmail()).get();
		Question question = questionRepository.findById(this.question.getQuestionId()).get();
		voteService.update(member, question.getQuestionId(), VoteType.UP);

		// when
		voteService.update(member, question.getQuestionId(), VoteType.NONE);

		// then
		assertFalse(voteRepository.existsByMemberAndQuestion(member, question));
	}

	@Test
	void 같은_타입으로_투표하면_변경되지_않는다() {
		// given
		Member member = memberRepository.findByEmail(this.member.getEmail()).get();
		Question question = questionRepository.findById(this.question.getQuestionId()).get();
		voteService.update(member, question.getQuestionId(), VoteType.UP);

		// when
		voteService.update(member, question.getQuestionId(), VoteType.UP);

		// then
		Optional<QuestionVote> optVote = voteRepository.findByMemberAndQuestion(member, question);
		assertTrue(optVote.isPresent());
		assertEquals(VoteType.UP, optVote.get().getType());
		assertEquals(optVote.get().getCreatedAt(),optVote.get().getLastModifiedAt());
	}
}