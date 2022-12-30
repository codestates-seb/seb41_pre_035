package com.codestates.sof.domain.answer.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.answer.dto.AnswerVoteDto;
import com.codestates.sof.domain.answer.entity.AnswerVote;
import com.codestates.sof.domain.answer.mapper.AnswerVoteMapper;
import com.codestates.sof.domain.answer.service.AnswerVoteService;
import com.codestates.sof.domain.auth.dto.MemberDetails;
import com.codestates.sof.global.dto.SingleResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/answers/{answer-id}/votes")
@Validated
@Slf4j
public class AnswerVoteController {
	private final AnswerVoteService answerVoteService;
	private final AnswerVoteMapper voteMapper;

	public AnswerVoteController(AnswerVoteService answerVoteService, AnswerVoteMapper voteMapper) {
		this.answerVoteService = answerVoteService;
		this.voteMapper = voteMapper;
	}

	@PatchMapping
	public ResponseEntity patchAnswerVote(@PathVariable("answer-id") @Positive long answerId,
		@RequestBody @Valid AnswerVoteDto.Patch requestBody,
		@AuthenticationPrincipal MemberDetails memberDetails) {

		AnswerVote answerVote =
			answerVoteService.updateAnswerVote(
				voteMapper.patchToAnswerVote(requestBody), memberDetails, answerId);

		AnswerVoteDto.Response response = voteMapper.answerVoteToResponse(answerVote);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}
}
