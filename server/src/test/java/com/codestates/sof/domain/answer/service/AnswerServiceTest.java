package com.codestates.sof.domain.answer.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.repository.AnswerRepository;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.repository.QuestionRepository;
import com.codestates.sof.domain.question.service.QuestionService;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@DisplayName("답변 테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnswerServiceTest {
	@Autowired
	QuestionService service;

	@Autowired
	AnswerService answerService;

	@Autowired
	QuestionRepository repository;

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	MemberRepository memberRepository;

	@Nested
	@DisplayName("답변 채택")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Acceptance {
		Question question;
		Member writer, member;
		Answer answer1, answer2;

		@BeforeAll
		void beforeAll() {
			clean();
			Member writer = new Member();
			writer.setName("name1");
			writer.setEmail("email1@email.com");
			writer.setEncryptedPassword("password");

			Member member = new Member();
			member.setName("name2");
			member.setEmail("email2@email.com");
			member.setEncryptedPassword("password");

			Question question = new Question(writer, "title", "question");
			Answer answer1 = new Answer(question, member, "answer1");
			Answer answer2 = new Answer(question, member, "answer2");

			this.writer = memberRepository.save(writer);
			this.member = memberRepository.save(member);

			this.question = repository.save(question);
			this.answer1 = answerRepository.save(answer1);
			this.answer2 = answerRepository.save(answer2);
		}

		@Transactional
		void clean() {
			repository.deleteAll();
			memberRepository.deleteAll();
		}

		@Test
		@Transactional(readOnly = true)
		void 질문작성자가_답변을_채택한다() {
			answerService.accept(writer, question.getQuestionId(), answer1.getAnswerId());
			Optional<Answer> optAnswer = answerRepository.findById(answer1.getAnswerId());

			assertTrue(optAnswer.isPresent());
			assertTrue(optAnswer.get().isAccepted());
			assertTrue(optAnswer.get().getQuestion().hasAcceptedAnswer());
		}

		@Test
		@Transactional(readOnly = true)
		void 작성자가_아니면_실패한다() {
			BusinessLogicException ex = assertThrows(
				BusinessLogicException.class,
				() -> answerService.accept(member, question.getQuestionId(), answer1.getAnswerId())
			);

			assertEquals(ExceptionCode.NO_PERMISSION_EDITING_QUESTION, ex.getExceptionCode());
		}

		@Test
		@Transactional(readOnly = true)
		void 답변을_채택한_질문은_다른답변을_채택할_수_없다() {
			answerService.accept(writer, question.getQuestionId(), answer1.getAnswerId());

			BusinessLogicException ex = assertThrows(
				BusinessLogicException.class,
				() -> answerService.accept(writer, question.getQuestionId(), answer2.getAnswerId())
			);

			assertEquals(ExceptionCode.NO_PERMISSION_EDITING_QUESTION, ex.getExceptionCode());
		}
	}
}