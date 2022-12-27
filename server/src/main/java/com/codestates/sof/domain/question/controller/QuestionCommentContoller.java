package com.codestates.sof.domain.question.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.question.dto.QuestionCommentDto;
import com.codestates.sof.domain.question.dto.QuestionCommentResponseDto;
import com.codestates.sof.domain.question.entity.QuestionComment;
import com.codestates.sof.domain.question.mapper.QuestionCommentMapper;
import com.codestates.sof.domain.question.service.QuestionCommentService;
import com.codestates.sof.global.dto.MultiResponseDto;
import com.codestates.sof.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

// TODO: /questions/{question-id}/comments/{comment-id} -> /questions/comments/{comment-id}

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions/{question-id}/comments")
public class QuestionCommentContoller {
	private final QuestionCommentService commentService;
	private final QuestionCommentMapper mapper;

	@PostMapping
	public ResponseEntity<SingleResponseDto<QuestionCommentResponseDto.Response>> post(
		@PathVariable("question-id") @Positive long questionId,
		@RequestBody @Valid QuestionCommentDto.Post post) {

		QuestionComment qc = commentService.comment(questionId, mapper.postToQuestionComment(post));
		QuestionCommentResponseDto.Response response = mapper.commentToResponse(qc);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	@PatchMapping("/{comment-id}")
	public ResponseEntity<SingleResponseDto<QuestionCommentResponseDto.Response>> patch(
		@PathVariable("question-id") @Positive long questionId,
		@PathVariable("comment-id") @Positive long commentId,
		@RequestBody @Valid QuestionCommentDto.Patch patch
	) {
		QuestionComment patchToComment = mapper.patchToQuestionComment(patch);
		patchToComment.setQuestionCommentId(commentId);

		QuestionComment comment = commentService.modify(questionId, patch.getModifierId(), patchToComment);
		QuestionCommentResponseDto.Response response = mapper.commentToResponse(comment);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<MultiResponseDto<QuestionCommentResponseDto.SimpleResponse>> getAll(
		@PathVariable("question-id") @Positive long questionId,
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "5") int size
	) {
		Page<QuestionComment> comments = commentService.findAll(questionId, page - 1, size);
		List<QuestionCommentResponseDto.SimpleResponse> responses = comments.map(mapper::commentToSimpleResponse).toList();
		return new ResponseEntity<>(new MultiResponseDto<>(responses, comments), HttpStatus.OK);
	}

	@DeleteMapping("/{comment-id}")
	public ResponseEntity<?> delete(
		@PathVariable("question-id") @Positive long questionId,
		@PathVariable("comment-id") @Positive long commentId
	) {
		commentService.delete(1L, questionId, commentId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
