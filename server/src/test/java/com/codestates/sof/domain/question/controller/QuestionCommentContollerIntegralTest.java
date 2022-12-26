package com.codestates.sof.domain.question.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

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
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.domain.member.service.MemberService;
import com.codestates.sof.domain.question.dto.QuestionCommentDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionComment;
import com.codestates.sof.domain.question.repository.QuestionCommentRepository;
import com.codestates.sof.domain.question.repository.QuestionRepository;
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
@DisplayName("질문 댓글 기능 테스트")
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
	MemberRepository memberRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	QuestionCommentRepository commentRepository;

	@Nested
	@DisplayName("댓글 등록")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Post {
		Question question;
		Member member;

		@BeforeAll
		void setUp() {
			saveMember();
			question = questionService.write(new Question(member, "title", "content"));
		}

		@Transactional
		void saveMember() {
			Member member = new Member();
			member.setName("name");
			member.setEmail("email");
			member.setName("name");
			member.setEncryptedPassword("encryptedPassword");

			this.member = memberRepository.findByEmail(member.getEmail())
				.orElseGet(() -> memberService.createMember(member));
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

	@Nested
	@DisplayName("댓글 수정")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Patch {

		TriFunction<Long, Long, Object, ResultActions> patchPerform = (questionId, commentId, content) ->
			mvc.perform(
				patch("/questions/{question-id}/comments/{comment-id}", questionId, commentId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(content))
			);

		QuestionComment comment;
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
			this.comment = commentService.comment(question.getQuestionId(),
				new QuestionComment(member, question, "content"));
		}

		@Test
		void 댓글을_수정하면_수정한_시간이_변경된다() throws Exception {
			// given
			QuestionCommentDto.Patch patch = QuestionCommentStub.getDefaultPatch();
			LocalDateTime modifiedAtBeforePatch = comment.getLastModifiedAt();

			// when
			ResultActions actions = patchPerform.apply(question.getQuestionId(), comment.getQuestionCommentId(), patch);

			// given
			actions
				.andExpect(status().isOk());

			Optional<QuestionComment> optComment = commentRepository.findById(comment.getQuestionCommentId());
			assertTrue(optComment.isPresent());
			assertTrue(optComment.get().getLastModifiedAt().isAfter(modifiedAtBeforePatch));
			assertEquals(patch.getContent(), optComment.get().getContent());
		}

		@Test
		void 댓글내용이_없으면_실패한다() throws Exception {
			// given
			QuestionCommentDto.Patch patch = QuestionCommentStub.getDefaultPatch();
			patch.setContent("");

			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<QuestionCommentDto.Patch>> validate = validator.validate(patch);

			// when
			ResultActions actions = patchPerform.apply(question.getQuestionId(), comment.getQuestionCommentId(), patch);

			// given
			actions
				.andExpect(status().isBadRequest());

			assertTrue(validate.iterator().hasNext());
			assertEquals("content", validate.iterator().next().getPropertyPath().toString());
		}

		@Test
		void 작성자가_아니면_실패한다() throws Exception {
			// given
			QuestionCommentDto.Patch patch = QuestionCommentStub.getDefaultPatch();
			patch.setModifierId((long)Integer.MAX_VALUE);

			// when
			ResultActions actions = patchPerform.apply(question.getQuestionId(), comment.getQuestionCommentId(), patch);

			// given
			actions
				.andExpect(status().isForbidden())
				.andExpect(res -> {
					Exception ex = res.getResolvedException();
					assertTrue(ex.getClass().isAssignableFrom(BusinessLogicException.class));
					assertEquals(
						ExceptionCode.NO_PERMISSION_EDITING_COMMENT,
						((BusinessLogicException)ex).getExceptionCode());
				});
		}

	}

	@FunctionalInterface
	interface TriFunction<A, B, C, D> {

		D apply(A a, B b, C c) throws Exception;
	}
}
