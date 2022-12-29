package com.codestates.sof.domain.answer.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.answer.dto.AnswerDto;
import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.mapper.AnswerMapper;
import com.codestates.sof.domain.answer.service.AnswerService;
import com.codestates.sof.domain.auth.dto.MemberDetails;
import com.codestates.sof.global.dto.MultiResponseDto;
import com.codestates.sof.global.dto.SingleResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/answers")
@Validated
@Slf4j
public class AnswerController {
	private final AnswerService answerService;
	private final AnswerMapper answerMapper;

	public AnswerController(AnswerService answerService, AnswerMapper answerMapper) {
		this.answerService = answerService;
		this.answerMapper = answerMapper;
	}

	@PostMapping
	public ResponseEntity postAnswer(@Valid @RequestBody AnswerDto.Post requestBody) {

		Answer answer =
			answerService.createAnswer(
				answerMapper.postToAnswer(requestBody));

		AnswerDto.Response response = answerMapper.answerToResponse(answer);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	@PatchMapping("/{answer-id}")
	public ResponseEntity patchAnswer(@Valid @RequestBody AnswerDto.Patch requestBody,
		@PathVariable("answer-id") @Positive long answerId,
		@AuthenticationPrincipal MemberDetails memberDetails) {

		Answer answer =
			answerService.updateAnswer(
				answerMapper.patchToAnswer(requestBody), memberDetails);

		AnswerDto.Response response = answerMapper.answerToResponse(answer);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity getAnswers(@RequestParam @Positive int page) {
		int size = 30;
		Page<Answer> pageAnswers = answerService.findAnswers(page - 1, size);
		List<Answer> answers = pageAnswers.getContent();
		List<AnswerDto.Response> responses = answerMapper.answersToResponses(answers);

		return new ResponseEntity<>(new MultiResponseDto<>(responses, pageAnswers), HttpStatus.OK);
	}

	@DeleteMapping("/{answer-id}")
	public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive long answerId,
		@AuthenticationPrincipal MemberDetails memberDetails) {

		answerService.deleteAnswer(answerId, memberDetails);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
