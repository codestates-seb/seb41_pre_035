package com.codestates.sof.domain.question.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import com.codestates.sof.domain.question.dto.QuestionDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionTag;
import com.codestates.sof.domain.question.repository.QuestionRepository;
import com.codestates.sof.domain.question.service.QuestionService;
import com.codestates.sof.domain.stub.QuestionStub;
import com.codestates.sof.domain.stub.TagStub;
import com.codestates.sof.domain.tag.entity.Tag;
import com.codestates.sof.domain.tag.service.TagService;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;
import com.codestates.sof.global.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionControllerIntegralTest {
	@Autowired
	MockMvc mvc;

	@Autowired
	QuestionController controller;

	@Autowired
	QuestionService service;

	@Autowired
	QuestionRepository repository;

	@Autowired
	MemberService memberService;

	@Autowired
	TagService tagService;

	@Autowired
	ObjectMapper om;

	@Autowired
	EntityManager em;

	Member member;

	List<String> tags = List.of("java", "javascript", "python");

	@BeforeAll
	void createTags() {
		TagStub.defaultTags().forEach(tagService::create);

		Member member = new Member();
		member.setName("name");
		member.setEmail("email@email.com");
		member.setEncryptedPassword("enc_password");
		this.member = memberService.createMember(member);
	}

	@Nested
	@DisplayName("Tests for the post method.")
	class Post {
		@Test
		void 질문을_작성하면_태그수가_1_증가한다() throws Exception {
			// given
			QuestionDto.Post post = (QuestionDto.Post)QuestionStub.Type.POST.create();

			Map<String, Long> tagCountBeforeSave = post.getTags().stream()
				.map(tagService::findBy)
				.collect(Collectors.toMap(Tag::getName, Tag::getTaggedCount));

			// when
			mvc.perform(TestUtils.POST.apply("/questions", om.writeValueAsString(post)));

			// then
			Map<String, Long> tagCountAfterSave = post.getTags().stream()
				.map(tagService::findBy)
				.collect(Collectors.toMap(Tag::getName, Tag::getTaggedCount));

			tagCountBeforeSave.forEach((key, value) -> {
				long before = value;
				long after = tagCountAfterSave.get(key);

				assertEquals(after, before + 1);
			});
		}

		@Test
		void 작성한뒤_isItWriter는_True이다() throws Exception {
			// given
			QuestionDto.Post post = (QuestionDto.Post)QuestionStub.Type.POST.create();

			// when
			ResultActions actions = mvc.perform(TestUtils.POST.apply("/questions", om.writeValueAsString(post)));

			// then
			actions
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.isItWriter").value(true))
				.andReturn();
		}

		@Test
		void 존재하지_않는_태그명이_포함되면_실패한다() throws Exception {
			// given
			QuestionDto.Post post = (QuestionDto.Post)QuestionStub.Type.POST.create();
			post.setTags(List.of("unknown"));

			// when
			ResultActions actions = mvc.perform(TestUtils.POST.apply("/questions", om.writeValueAsString(post)));

			// then
			actions
				.andExpect(status().isNotFound())
				.andExpect(res -> {
					Exception exception = res.getResolvedException();
					assertTrue(exception.getClass().isAssignableFrom(BusinessLogicException.class));
					assertEquals(ExceptionCode.NOT_FOUND_TAG, ((BusinessLogicException)exception).getExceptionCode());
				})
				.andReturn();
		}

		@Test
		void 익명_사용자는_질문을_작성할수_없다() {
			// TODO (Auth)
		}

		@Test
		void 미인증_사용자는_질문을_작성할수_없다() {
			// TODO (Auth)
		}
	}

	@Nested
	@DisplayName("Tests for the find method.")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Find {
		Long questionId = 1L;

		@BeforeAll
		void writeQuestion() {
			if (repository.findById(questionId).isEmpty()) {
				Question question = new Question(member, "title", "content");
				tags.forEach(question::addTag);
				service.write(question);
			}
		}

		@Test
		void 존재하는_아이디로_검색시_성공한다() throws Exception {
			mvc
				.perform(get("/questions/{question-id}", questionId))
				.andExpect(status().isOk());
		}

		@Test
		void 검색된_질문은_조회수가_1_증가한다() throws Exception {
			long viewCountBeforeGet = repository.findById(questionId).get().getViewCount();

			ResultActions actions = mvc.perform(get("/questions/{question-id}", questionId));

			actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.viewCount").value(viewCountBeforeGet + 1));
		}

		@Test
		void 요청자와_작성자가_같으면_isItWriter는_True이다() throws Exception {
			// TODO (Auth)
		}

		@Test
		void 검색된_질문에_투표를_했다면_투표_타입을_반환한다() throws Exception {
			// TODO (Auth)
		}

		@Test
		void 채택된_답변들의_아이디를_반환한다() throws Exception {
			// TODO (Adoption)
		}

		@Test
		void 존재하지_않는_식별자로_검색시_실패한다() throws Exception {
			mvc
				.perform(get("/questions/{question-id}", Integer.MAX_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(res -> {
					Exception ex = res.getResolvedException();
					assertTrue(ex.getClass().isAssignableFrom(BusinessLogicException.class));
					assertEquals(ExceptionCode.NOT_FOUND_QUESTION, ((BusinessLogicException)ex).getExceptionCode());
				});
		}
	}

	@Nested
	@DisplayName("Tests for the patch method.")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Patch {
		long questionId = 1L;

		ThrowableFunction<Object, ResultActions> patchActions = (content) -> mvc.perform(
			patch("/questions/{question-id}", questionId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(content))
		);

		@BeforeAll
		void writeQuestion() {
			if (repository.findById(questionId).isEmpty()) {
				Question question = new Question(member, "title", "content");
				tags.forEach(question::addTag);
				service.write(question);
			}
		}

		@Test
		@Transactional
		void 작성자는_질문을_수정할수있다() throws Exception {
			// given
			QuestionDto.Patch patch = (QuestionDto.Patch)QuestionStub.Type.PATCH.create();
			patch.setTags(Collections.emptyList());
			Question before = service.findById(questionId);

			// when
			em.clear();
			patchActions.apply(patch);
			Question after = service.findById(questionId);

			// then
			assertNotSame(after, before);
			assertNotEquals(before.getTitle(), after.getTitle());
			assertNotEquals(before.getContent(), after.getContent());
			assertTrue(after.getTags().isEmpty());
		}

		@Test
		@Transactional
		void 답변이나_댓글은_반영되지_않는다() throws Exception {
			// TODO (Comment)
		}

		@Test
		@Transactional
		void 태깅수는_추가되면_1증가하고_삭제되면_1감소한다() throws Exception {
			// given
			Question before = service.findById(questionId);
			QuestionDto.Patch patch = (QuestionDto.Patch)QuestionStub.Type.PATCH.create();
			Function<Map.Entry<String, Long>, Long> getTaggedCount = tag -> tagService.findBy(tag.getKey())
				.getTaggedCount();
			Map<String, Long> countsBeforePatch = before.getTags().stream()
				.collect(Collectors.toMap(QuestionTag::getTagName, tag -> tag.getTag().getTaggedCount()));

			// when
			em.clear();
			patchActions.apply(patch);
			Question after = service.findById(questionId);
			Map<String, Long> countsAfterPatch = after.getTags().stream()
				.collect(Collectors.toMap(QuestionTag::getTagName, tag -> tag.getTag().getTaggedCount()));

			// then
			// 1. 삭제되지 않은 기존 태그
			countsAfterPatch.entrySet().stream()
				.filter(tag -> countsBeforePatch.containsKey(tag.getKey()))
				.forEach(alived -> assertEquals(getTaggedCount.apply(alived), alived.getValue()));

			// 2. 추가된 태그
			countsAfterPatch.entrySet().stream()
				.filter(tag -> !countsBeforePatch.containsKey(tag.getKey()))
				.forEach(alived -> assertEquals(getTaggedCount.apply(alived), alived.getValue() + 1));

			// 3. 삭제된 태그
			countsBeforePatch.entrySet().stream()
				.filter(tag -> !countsAfterPatch.containsKey(tag.getKey()))
				.forEach(alived -> assertEquals(getTaggedCount.apply(alived), alived.getValue() - 1));
		}

		@Test
		void 작성자가_아니면_실패한다() throws Exception {
			QuestionDto.Patch patch = (QuestionDto.Patch)QuestionStub.Type.PATCH.create();
			patch.setMemberId((long)Integer.MAX_VALUE);

			ResultActions actions = patchActions.apply(patch);

			actions
				.andExpect(status().isForbidden())
				.andExpect(res -> {
					Exception ex = res.getResolvedException();
					assertTrue(ex.getClass().isAssignableFrom(BusinessLogicException.class));
					assertEquals(ExceptionCode.NO_PERMISSION_EDITING_QUESTION,
						((BusinessLogicException)ex).getExceptionCode());
				});
		}

		@Test
		void 제목이나_본문이_없으면_실패한다() throws Exception {
			QuestionDto.Patch patch = (QuestionDto.Patch)QuestionStub.Type.PATCH.create();
			patch.setTitle(null);
			patch.setContent(null);

			ResultActions actions = patchActions.apply(patch);

			actions
				.andExpect(status().isBadRequest());
		}
	}

	// TODO (Auth, Comment)
	@Nested
	@DisplayName("Tests for the delete method.")
	class Delete {
		@Test
		void 작성자는_질문을_삭제할수있다() {

		}

		@Test
		void 질문을_삭제하면_댓글과_답변도_삭제된다() {

		}

		@Test
		void 작성자가_아니면_실패한다() throws Exception {

		}
	}

	@FunctionalInterface
	interface ThrowableFunction<T, R> {
		R apply(T data) throws Exception;
	}
}