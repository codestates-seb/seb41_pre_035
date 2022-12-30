package com.codestates.sof.domain.question.controller;

import java.util.List;

import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.dto.QuestionResponseDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.mapper.QuestionMapper;
import com.codestates.sof.domain.question.service.BookmarkService;
import com.codestates.sof.global.dto.MultiResponseDto;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class BookmarkController {
	private final BookmarkService bookmarkService;
	private final QuestionMapper mapper;

	@PostMapping("/questions/{question-id}/bookmarks")
	public ResponseEntity<?> bookmark(
		@RequestParam(name = "isUndo", defaultValue = "false") boolean isUndo,
		@PathVariable("question-id") @Positive long questionId,
		@AuthenticationPrincipal Member member
	) {
		boolean isBookmarked = bookmarkService.bookmark(member, questionId, isUndo);
		return new ResponseEntity<>(isBookmarked ? HttpStatus.CREATED : HttpStatus.NO_CONTENT);
	}

	@GetMapping("/members/bookmarks")
	public ResponseEntity<?> getAll(
		@RequestParam(name = "page", defaultValue = "1") @Positive int page,
		@RequestParam(name = "size", defaultValue = "10") @Positive int size,
		@AuthenticationPrincipal Member member
	) {
		Page<Question> questions = bookmarkService.getAll(member, page - 1, size);
		List<QuestionResponseDto.SimpleResponse> response = mapper.questionsToResponses(questions, member);

		return new ResponseEntity<>(new MultiResponseDto<>(response, questions), HttpStatus.OK);
	}
}
