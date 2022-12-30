package com.codestates.sof.domain.question.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.member.entity.Member;
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
// - GET ALL: 전체 조회시 Simple Response Dto로 응답

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
	private final QuestionService questionService;
	private final QuestionMapper mapper;

	// *** Command ***

	@PostMapping
	public ResponseEntity<SingleResponseDto<QuestionResponseDto.Response>> post(
		@AuthenticationPrincipal Member member,
		@Valid @RequestBody QuestionRequestDto.Post post
	) {
		Question question = questionService.write(mapper.postToQuestion(post), member);
		QuestionResponseDto.Response response = mapper.questionToResponse(question);
		mapper.setPropertiesToResponse(member, question, response);

		return ResponseEntity.status(HttpStatus.CREATED).body(new SingleResponseDto<>(response));
	}

	@PatchMapping("/{question-id}")
	public ResponseEntity<SingleResponseDto<QuestionResponseDto.Response>> patch(
		@AuthenticationPrincipal Member member,
		@PathVariable("question-id") @Positive Long questionId,
		@RequestBody @Valid QuestionRequestDto.Patch patch
	) {
		Question question = questionService.patch(questionId, member, mapper.patchToQuestion(patch));
		QuestionResponseDto.Response response = mapper.questionToResponse(question);
		mapper.setPropertiesToResponse(member, question, response);

		return ResponseEntity.ok(new SingleResponseDto<>(response));
	}

	@DeleteMapping("/{question-id}")
	public ResponseEntity<?> delete(@AuthenticationPrincipal Member member,
		@PathVariable("question-id") @Positive Long questionId) {
		questionService.delete(member, questionId);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{question-id}")
	public ResponseEntity<SingleResponseDto<QuestionResponseDto.Response>> get(
		@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member,
		@PathVariable("question-id") @Positive Long questionId
	) {
		Question question = questionService.findById(questionId);
		QuestionResponseDto.Response response = mapper.questionToResponse(question);
		mapper.setPropertiesToResponse(member, question, response);

		return ResponseEntity.ok(new SingleResponseDto<>(response));
	}

	// *** Query ***

	@GetMapping
	public ResponseEntity<?> getAll(
		@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member,
		QuestionPageRequest pageRequest
	) {
		return getMultiResponseEntity(member, questionService.findAll(pageRequest));
	}

	@GetMapping("/tagged/{tag-name}")
	public ResponseEntity<?> getAllByTag(
		@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member,
		@PathVariable("tag-name") String tagName,
		QuestionPageRequest pageRequest
	) {
		return getMultiResponseEntity(member, questionService.findAllByTag(tagName, pageRequest));
	}

	@GetMapping("search")
	public ResponseEntity<?> searchByQuery(
		@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Member member,
		@RequestParam(name = "q") String query,
		QuestionPageRequest pageRequest
	) {
		return getMultiResponseEntity(member, questionService.search(query, pageRequest));
	}

	private ResponseEntity<?> getMultiResponseEntity(Member member, Page<Question> page) {
		if (page.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			List<QuestionResponseDto.SimpleResponse> responses = mapper.questionsToResponses(page, member);
			return new ResponseEntity<>(new MultiResponseDto<>(responses, page), HttpStatus.OK);
		}
	}
}
