package com.codestates.sof.domain.question.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.sof.domain.question.dto.QuestionDto;
import com.codestates.sof.domain.question.entity.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
	Question postToQuestion(QuestionDto.Post post);

	@Mapping(target = "isItWriter", expression = "java(question.isItWriter())")
	@Mapping(target = "hasAlreadyVoted", expression = "java(question.hasAlreadyVoted())")
	QuestionDto.Response questionToResponse(Question question);
}
