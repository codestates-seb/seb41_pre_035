package com.codestates.sof.domain.question.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.service.MemberService;
import com.codestates.sof.domain.question.dto.QuestionCommentDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionComment;
import com.codestates.sof.domain.question.repository.QuestionCommentRepository;
import com.codestates.sof.domain.question.service.QuestionCommentService;
import com.codestates.sof.domain.question.service.QuestionService;
import com.codestates.sof.domain.stub.QuestionCommentStub;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionCommentContollerIntegralTest {
	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper om;

	@Autowired
	EntityManager em;

	@Autowired
	QuestionCommentContoller contoller;

	@Autowired
	MemberService memberService;

	@Autowired
	QuestionService questionService;

	@Autowired
	QuestionCommentService commentService;

	@Autowired
	QuestionCommentRepository repository;

	@Nested
	@DisplayName("Tests for the post method")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Post {
		Question question;
		Member member;

		@BeforeAll
		void setUp() {
			Member member = new Member();
			member.setName("name");
			member.setEmail("email");
			member.setName("name");
			member.setEncryptedPassword("encryptedPassword");

			this.member = memberService.createMember(member);
			this.question = questionService.write(new Question(member, "title", "content"));
		}

		@Test
		@Transactional(readOnly = true)
		void 회원은_댓글을_등록할_수_있다() throws Exception {
			// given
			QuestionCommentDto.Post post = QuestionCommentStub.getDefaultPost();

			// when
			mvc.perform(
				post("/questions/{question-id}/comments", question.getQuestionId())
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(post))
			);

			// given
			Question question = questionService.findByIdWithoutIncreasingViewCount(this.question.getQuestionId());

			assertFalse(question.getComments().isEmpty());

			QuestionComment comment = question.getComments().get(0);
			assertEquals(post.getContent(), comment.getContent());
			assertEquals(member.getMemberId(), comment.getMember().getMemberId());
			assertEquals(question.getQuestionId(), comment.getQuestion().getQuestionId());
		}

		@Test
		void 존재하지_않은_질문에_댓글을_등록할_수_없다() throws Exception {
			// given
			QuestionCommentDto.Post post = QuestionCommentStub.getDefaultPost();
			Long noExistsQuestionId = (long)Integer.MAX_VALUE;

			// when
			ResultActions actions = mvc.perform(
				post("/questions/{question-id}/comments", noExistsQuestionId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(post))
			);

			// given
			actions
				.andExpect(status().isNotFound())
				.andExpect(res -> {
					Exception ex = res.getResolvedException();
					assertTrue(ex.getClass().isAssignableFrom(BusinessLogicException.class));
					assertEquals(ExceptionCode.NOT_FOUND_QUESTION, ((BusinessLogicException)ex).getExceptionCode());
				});
		}

		@Test
		void 삭제된_질문에_댓글을_등록할_수_없다() throws Exception {
			// given
			QuestionCommentDto.Post post = QuestionCommentStub.getDefaultPost();
			Long noExistsQuestionId = (long)Integer.MAX_VALUE;

			// when
			ResultActions actions = mvc.perform(
				post("/questions/{question-id}/comments", noExistsQuestionId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(post))
			);

			// given
			actions
				.andExpect(status().isNotFound())
				.andExpect(res -> {
					Exception ex = res.getResolvedException();
					assertTrue(ex.getClass().isAssignableFrom(BusinessLogicException.class));
					assertEquals(ExceptionCode.NOT_FOUND_QUESTION, ((BusinessLogicException)ex).getExceptionCode());
				});
		}

		@Test
		void 회원이_아니면_댓글을_등록할_수_없다() throws Exception {
			// TODO (Auth)
		}
	}
}
