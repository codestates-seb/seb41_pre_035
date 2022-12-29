package com.codestates.sof.domain.question.controller;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.dto.QuestionVoteDto;
import com.codestates.sof.domain.question.service.QuestionVoteService;
import com.codestates.sof.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionVoteController {
	private final QuestionVoteService voteService;

	@PatchMapping("/{question-id}/votes")
	public ResponseEntity<SingleResponseDto<?>> patch(
		@PathVariable("question-id") @Positive long questionId,
		@RequestBody @Valid QuestionVoteDto.Post post,
		@AuthenticationPrincipal Member member
	) {
		int voteCount = voteService.update(member, questionId, post.getVoteType());

		Map<String, Integer> response = Collections.singletonMap("voteCount", voteCount);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}
}
