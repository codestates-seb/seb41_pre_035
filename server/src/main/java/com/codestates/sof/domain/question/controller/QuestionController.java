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
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.question.dto.QuestionRequestDto;
import com.codestates.sof.domain.question.dto.QuestionResponseDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.mapper.QuestionMapper;
import com.codestates.sof.domain.question.service.QuestionService;
import com.codestates.sof.domain.question.support.QuestionPageRequest;
import com.codestates.sof.global.dto.MultiResponseDto;
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
	public ResponseEntity<SingleResponseDto<QuestionResponseDto.Response>> post(@Valid @RequestBody QuestionRequestDto.Post post) {
		Question question = mapper.postToQuestion(post);
		QuestionResponseDto.Response response = mapper.questionToResponse(questionService.write(question));
		response.setIsItWriter(true);
		return ResponseEntity.status(HttpStatus.CREATED).body(new SingleResponseDto<>(response));
	}

	@PatchMapping("/{question-id}")
	public ResponseEntity<SingleResponseDto<QuestionResponseDto.Response>> patch(
		@PathVariable("question-id") @Positive Long questionId,
		@RequestBody @Valid QuestionRequestDto.Patch patch) {

		Question question = questionService.patch(questionId, patch.getMemberId(), mapper.patchToQuestion(patch));
		QuestionResponseDto.Response response = mapper.questionToResponse(question);

		return ResponseEntity.ok(new SingleResponseDto<>(response));
	}

	@GetMapping("/{question-id}")
	public ResponseEntity<SingleResponseDto<QuestionResponseDto.Response>> get(@PathVariable("question-id") @Positive Long questionId) {
		Question question = questionService.findById(questionId);
		QuestionResponseDto.Response response = mapper.questionToResponse(question);
		return ResponseEntity.ok(new SingleResponseDto<>(response));
	}

	@GetMapping
	public ResponseEntity<MultiResponseDto<QuestionResponseDto.SimpleResponse>> getAll(QuestionPageRequest pageRequest) {
		Page<Question> page = questionService.findAll(pageRequest);
		List<QuestionResponseDto.SimpleResponse> response = page.map(mapper::questionToSimpleResponse).toList();
		return new ResponseEntity<>(new MultiResponseDto<>(response, page), HttpStatus.OK);
	}

	@GetMapping("/tags/{tag-name}")
	public ResponseEntity<MultiResponseDto<QuestionResponseDto.SimpleResponse>> getAllByTag(
		@PathVariable("tag-name") String tagName, QuestionPageRequest pageRequest
	) {
		Page<Question> page = questionService.findAllByTag(tagName, pageRequest);
		List<QuestionResponseDto.SimpleResponse> response = page.map(mapper::questionToSimpleResponse).toList();
		return new ResponseEntity<>(new MultiResponseDto<>(response, page), HttpStatus.OK);
	}

	@DeleteMapping("/{question-id}")
	public ResponseEntity<?> delete(@PathVariable("question-id") @Positive Long questionId) {
		Long memberId = 0L;

		questionService.delete(memberId, questionId);

		return ResponseEntity.noContent().build();
	}
}
