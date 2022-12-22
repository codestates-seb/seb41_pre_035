package com.codestates.sof.domain.question.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.question.dto.QuestionDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.mapper.QuestionMapper;
import com.codestates.sof.domain.question.service.QuestionService;

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
	public ResponseEntity<QuestionDto.Response> post(@Valid @RequestBody QuestionDto.Post post) {
		Question question = mapper.postToQuestion(post);
		QuestionDto.Response response = mapper.questionToResponse(questionService.write(question));
		response.setIsItWriter(true);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
