package com.codestates.sof.domain.question.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.repository.BookmarkRepository;
import com.codestates.sof.domain.question.repository.QuestionRepository;

@SpringBootTest
@ActiveProfiles("local")
@DisplayName("북마크 테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookmarkServiceTest {
	@Autowired
	BookmarkService service;

	@Autowired
	BookmarkRepository repository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	QuestionRepository questionRepository;

	Member member;
	Question question1, question2;

	@BeforeAll
	@Transactional
	void beforeAll() {
		removeAll();
		member = new Member();
		member.setName("name");
		member.setEmail("email");
		member.setEncryptedPassword("password");
		member = memberRepository.save(member);

		question1 = new Question(member, "question1", "content");
		question1 = questionRepository.save(question1);

		question2 = new Question(member, "question2", "content");
		question2 = questionRepository.save(question2);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	void removeAll() {
		questionRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@AfterEach
	@Transactional
	void afterEach() {
		repository.deleteAll();
	}

	@Test
	@Order(1)
	void 북마킹한_질문목록을_조회한다() {
		service.bookmark(member, question1.getQuestionId(), false);
		service.bookmark(member, question2.getQuestionId(), false);

		Page<Question> bookmarkedQuestions =
			assertDoesNotThrow(() -> service.getAll(member, 0, 5));

		assertEquals(1, bookmarkedQuestions.getTotalPages());
		assertEquals(2, bookmarkedQuestions.getTotalElements());
	}

	@Test
	void 북마킹을_한다() {
		assertTrue(service.bookmark(member, question1.getQuestionId(), false));

		Page<Question> page = assertDoesNotThrow(() -> service.getAll(member, 0, 5));

		assertFalse(page.isEmpty());
		assertEquals(question1.getQuestionId(), page.getContent().get(0).getQuestionId());
	}

	@Test
	void 북마킹을_취소한다() {
		assertTrue(service.bookmark(member, question1.getQuestionId(), false));
		assertFalse(service.bookmark(member, question1.getQuestionId(), true));

		Page<Question> page = assertDoesNotThrow(() -> service.getAll(member, 0, 5));

		assertTrue(page.isEmpty());
	}

	@Test
	void 북마킹을_한_질문에_북마크를_하면_무시한다() {
		assertTrue(service.bookmark(member, question1.getQuestionId(), false));
		assertTrue(service.bookmark(member, question1.getQuestionId(), false));

		Page<Question> page = assertDoesNotThrow(() -> service.getAll(member, 0, 5));

		assertFalse(page.getContent().isEmpty());
		assertEquals(1, page.getTotalElements());
	}

	@Test
	void 북마킹을_하지않은_질문에_북마크를_취소하면_무시한다() {
		assertFalse(service.bookmark(member, question1.getQuestionId(), true));

		Page<Question> page = assertDoesNotThrow(() -> service.getAll(member, 0, 5));

		assertTrue(page.isEmpty());
	}
}