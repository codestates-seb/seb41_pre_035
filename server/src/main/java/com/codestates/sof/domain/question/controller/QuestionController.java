package com.codestates.sof.domain.question.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
	private final QuestionService questionService;
	private final QuestionMapper mapper;

	// TODO: authentication member의 정보가 필요함
	@PostMapping
	public ResponseEntity<SingleResponseDto<QuestionDto.Response>> post(@Valid @RequestBody QuestionDto.Post post) {
		Question question = mapper.postToQuestion(post);
		QuestionDto.Response response = mapper.questionToResponse(questionService.write(question));
		response.setIsItWriter(true);
		return ResponseEntity.status(HttpStatus.CREATED).body(new SingleResponseDto<>(response));
	}

	@GetMapping("/{question-id}")
	public ResponseEntity<SingleResponseDto<QuestionDto.Response>> get(@PathVariable("question-id") @Min(0) Long questionId) {
		Question question = questionService.findById(questionId);
		QuestionDto.Response response = mapper.questionToResponse(question);
		return ResponseEntity.ok(new SingleResponseDto<>(response));
	}

	@PatchMapping("/{question-id}")
	public ResponseEntity<SingleResponseDto<QuestionDto.Response>> patch(
		@RequestBody @Valid QuestionDto.Patch patch,
		@PathVariable("question-id") @Min(0) Long questionId) {

		// TODO Auth user id로 바꿔야됨
		Question question = questionService.patch(questionId, patch.getMemberId(), mapper.patchToQuestion(patch));
		QuestionDto.Response response = mapper.questionToResponse(question);

		return ResponseEntity.ok(new SingleResponseDto<>(response));
	}

	@DeleteMapping("/{question-id}")
	public ResponseEntity<?> delete(@PathVariable("question-id") @Min(0) Long questionId) {
		Long memberId = 0L; // TODO Auth user id

		questionService.delete(memberId, questionId);

		return ResponseEntity.noContent().build();
	}
}
