package com.codestates.sof.domain.question.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.question.dto.QuestionCommentDto;
import com.codestates.sof.domain.question.entity.QuestionComment;
import com.codestates.sof.domain.question.mapper.QuestionCommentMapper;
import com.codestates.sof.domain.question.service.QuestionCommentService;
import com.codestates.sof.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions/{question-id}/comments")
public class QuestionCommentContoller {
	private final QuestionCommentService questionCommentService;
	private final QuestionCommentMapper mapper;

	@PostMapping
	public ResponseEntity<SingleResponseDto<QuestionCommentDto.Response>> post(
		@PathVariable("question-id") @Positive long questionId,
		@RequestBody @Valid QuestionCommentDto.Post post) {

		QuestionComment qc = mapper.postToQuestionComment(post);
		QuestionCommentDto.Response response = mapper.questionCommentToResponse(qc);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}
}
