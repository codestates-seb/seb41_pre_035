package com.codestates.sof.domain.question.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.service.MemberService;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionTag;
import com.codestates.sof.domain.stub.TagStub;
import com.codestates.sof.domain.tag.entity.Tag;
import com.codestates.sof.domain.tag.service.TagService;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

@SpringBootTest
@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuestionServiceTest {
	@Autowired
	QuestionService service;

	@Autowired
	TagService tagService;

	@Autowired
	MemberService memberService;

	List<String> tags = List.of("java", "javascript", "python");

	Member member;

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
	@DisplayName("Tests for the write method.")
	class Write {
		@Test
		@Order(1)
		void 질문을_작성하면_태그수가_1_증가한다() {
			// given
			Question question = new Question(member, "title", "content");
			tags.forEach(question::addTag);

			Map<String, Long> tagCountBeforeSave = question.getTags().stream()
				.map(QuestionTag::getTag)
				.collect(Collectors.toMap(Tag::getName, Tag::getTaggedCount));

			// when
			Question newQuestion = service.write(question);
			Map<String, Long> tagCountAfterSave = newQuestion.getTags().stream()
				.map(QuestionTag::getTag)
				.collect(Collectors.toMap(Tag::getName, Tag::getTaggedCount));

			// then
			tagCountBeforeSave.forEach((key, value) -> {
				long before = value;
				long after = tagCountAfterSave.get(key);

				assertEquals(after, before + 1);
			});
		}

		@Test
		@Order(2)
		void 존재하지_않는_태그명이_포함되면_실패한다() {
			// given
			Question question = new Question(member, "title", "content");
			List.of("unknown").forEach(question::addTag);

			// when & then
			BusinessLogicException ex = assertThrows(BusinessLogicException.class, () -> service.write(question));
			assertEquals(ExceptionCode.NOT_FOUND_TAG, ex.getExceptionCode());
		}
	}

	@Test
	void findById() {
	}

	@Test
	void patch() {
	}

	@Test
	void delete() {
	}
}