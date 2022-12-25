package com.codestates.sof.domain.question.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.question.dto.QuestionDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.mapper.QuestionMapper;
import com.codestates.sof.domain.question.service.QuestionService;
import com.codestates.sof.global.dto.SingleResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO(얘기 필요): Response Dto는 Find 메서드에서만 반환하고, 나머지는 Http Status만 주기
// TODO (Auth, Adoption)
// - Auth: 작성자와 요청자가 일치하는지 확인필요
// - Adoption: 채택만을 위한 핸들러 작성
// - GET ALL: 전체 조회시 Simple Response Dto로 응답

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
	private final QuestionService questionService;
	private final QuestionMapper mapper;

	@PostMapping
	public ResponseEntity<SingleResponseDto<QuestionDto.Response>> post(@Valid @RequestBody QuestionDto.Post post) {
		Question question = mapper.postToQuestion(post);
		QuestionDto.Response response = mapper.questionToResponse(questionService.write(question));
		response.setIsItWriter(true);
		return ResponseEntity.status(HttpStatus.CREATED).body(new SingleResponseDto<>(response));
	}

	@GetMapping("/{question-id}")
	public ResponseEntity<SingleResponseDto<QuestionDto.Response>> get(@PathVariable("question-id") @Positive Long questionId) {
		Question question = questionService.findById(questionId);
		QuestionDto.Response response = mapper.questionToResponse(question);
		return ResponseEntity.ok(new SingleResponseDto<>(response));
	}

	@PatchMapping("/{question-id}")
	public ResponseEntity<SingleResponseDto<QuestionDto.Response>> patch(
		@PathVariable("question-id") @Positive Long questionId,
		@RequestBody @Valid QuestionDto.Patch patch) {

		Question question = questionService.patch(questionId, patch.getMemberId(), mapper.patchToQuestion(patch));
		QuestionDto.Response response = mapper.questionToResponse(question);

		return ResponseEntity.ok(new SingleResponseDto<>(response));
	}

	@DeleteMapping("/{question-id}")
	public ResponseEntity<?> delete(@PathVariable("question-id") @Positive Long questionId) {
		Long memberId = 0L;

		questionService.delete(memberId, questionId);

		return ResponseEntity.noContent().build();
	}
}
