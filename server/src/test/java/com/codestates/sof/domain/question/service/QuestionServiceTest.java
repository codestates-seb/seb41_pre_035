package com.codestates.sof.domain.question.service;

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
import com.codestates.sof.domain.answer.service.AnswerService;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.repository.QuestionCommentRepository;
import com.codestates.sof.domain.question.repository.QuestionRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@DisplayName("질문 기능 테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionServiceTest {
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

	@Autowired
	QuestionCommentRepository commentRepository;

	@Nested
	@DisplayName("질문 등록")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Acceptance {
		Question question;
		Member writer, member;
		Answer answer1, answer2;

		@BeforeAll
		void beforeAll() {
			cleanUp();
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
		void cleanUp() {
			repository.deleteAll();
			memberRepository.deleteAll();
		}

		@Test
		void 회원이_질문을_작성한다() {

		}

		@Test
		void 비회원이면_실패한다() {

		}

		@Test
		void 질문을_작성하면_태그수가_1_증가한다() throws Exception {
		}

		@Test
		void 작성한뒤_isItWriter는_True이다() throws Exception {
		}

		@Test
		void 존재하지_않는_태그명이_포함되면_실패한다() throws Exception {
		}

		@Test
		void 익명_사용자는_질문을_작성할수_없다() throws Exception {
		}

		@Test
		void 미인증_사용자는_질문을_작성할수_없다() throws Exception {
		}
	}

	@Nested
	@DisplayName("질문 개별 조회")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Find {
		@Test
		void 존재하는_아이디로_검색시_성공한다() throws Exception {
		}

		@Test
		void 검색된_질문은_조회수가_1_증가한다() throws Exception {
		}

		@Test
		void 요청자와_작성자가_같으면_isItWriter는_True이다() throws Exception {
		}

		@Test
		void 검색된_질문에_투표를_했다면_투표_타입을_반환한다() throws Exception {
		}

		@Test
		void 채택된_답변들의_아이디를_반환한다() throws Exception {
		}

		@Test
		void 존재하지_않는_식별자로_검색시_실패한다() throws Exception {
		}
	}

	@Nested
	@DisplayName("질문 수정")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Patch {
		@Test
		void 작성자는_질문을_수정할수있다() throws Exception {
		}

		@Test
		void 답변이나_댓글은_반영되지_않는다() throws Exception {
		}

		@Test
		void 태깅수는_추가되면_1증가하고_삭제되면_1감소한다() throws Exception {
		}

		@Test
		void 작성자가_아니면_실패한다() throws Exception {
		}

		@Test
		void 제목이나_본문이_없으면_실패한다() throws Exception {
		}

		@Test
		void 채택된_답변은_수정할_수_없다() throws Exception {
		}
	}

	@Nested
	@DisplayName("질문 삭제")
	class Delete {
		@Test
		void 작성자는_질문을_삭제할수있다() throws Exception {
		}

		@Test
		void 질문을_삭제하면_댓글과_답변도_삭제된다() throws Exception {
		}

		@Test
		void 작성자가_아니면_실패한다() throws Exception {
		}
		
		@Test
		void 채택된_답변은_삭제할_수_없다() throws Exception {
		}
	}
}