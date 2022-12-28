package com.codestates.sof.domain.question.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@DisplayName("질문 댓글 기능 테스트")
public class QuestionCommentContollerIntegrationTest {
	@Nested
	@DisplayName("댓글 등록")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Post {
		@Test
		void 회원은_댓글을_등록할_수_있다() throws Exception {
		}

		@Test
		void 존재하지_않은_질문에_댓글을_등록할_수_없다() throws Exception {
		}

		@Test
		void 삭제된_질문에_댓글을_등록할_수_없다() throws Exception {
		}

		@Test
		void 회원이_아니면_댓글을_등록할_수_없다() throws Exception {
		}
	}

	@Nested
	@DisplayName("댓글 수정")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Patch {
		@Test
		void 댓글을_수정하면_수정한_시간이_변경된다() throws Exception {
		}

		@Test
		void 댓글내용이_없으면_실패한다() throws Exception {
		}

		@Test
		void 작성자가_아니면_실패한다() throws Exception {
		}
	}

	@Nested
	@DisplayName("댓글 삭제")
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	class Delete {
		@Test
		void 작성자는_댓글을_삭제할_수_있다() throws Exception {
		}

		@Test
		void 작성자가_아니면_실패한다() throws Exception {
		}
	}
}
