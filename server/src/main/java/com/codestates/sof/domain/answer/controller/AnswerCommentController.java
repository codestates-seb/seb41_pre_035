package com.codestates.sof.domain.answer.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.answer.dto.AnswerCommentDto;
import com.codestates.sof.domain.answer.entity.AnswerComment;
import com.codestates.sof.domain.answer.mapper.AnswerCommentMapper;
import com.codestates.sof.domain.answer.service.AnswerCommentService;
import com.codestates.sof.global.dto.SingleResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/answers/{answer-id}/comments")
@Validated
@Slf4j
public class AnswerCommentController {
	private final AnswerCommentService commentService;
	private final AnswerCommentMapper commentMapper;

	public AnswerCommentController(AnswerCommentService commentService, AnswerCommentMapper commentMapper) {
		this.commentService = commentService;
		this.commentMapper = commentMapper;
	}

	@PostMapping
	public ResponseEntity postComment(@RequestBody @Valid AnswerCommentDto.Post requestBody,
		@PathVariable("answer-id") @Positive long answerId) {

		AnswerComment comment = commentMapper.postToComment(requestBody);
		AnswerComment savedComment = commentService.createComment(comment, answerId);
		AnswerCommentDto.Response response = commentMapper.commentToResponse(savedComment);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	@PatchMapping("/{comment-id}")
	public ResponseEntity patchComment(@RequestBody @Valid AnswerCommentDto.Patch requestBody,
		@PathVariable("answer-id") @Positive long answerId,
		@PathVariable("comment-id") @Positive long commentId) {

		AnswerComment comment = commentMapper.patchToComment(requestBody);
		AnswerComment updatedComment = commentService.updateComment(comment, commentId);
		AnswerCommentDto.Response response = commentMapper.commentToResponse(updatedComment);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}
}
