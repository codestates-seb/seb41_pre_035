package com.codestates.sof.domain.answer.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.codestates.sof.domain.answer.dto.AnswerDto;
import com.codestates.sof.domain.answer.entity.Answer;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
	Answer postToAnswer(AnswerDto.Post requestBody);

	Answer patchToAnswer(AnswerDto.Patch requestBody);

	AnswerDto.Response answerToResponse(Answer answer);

	List<AnswerDto.Response> answersToResponses(List<Answer> answers);
}
