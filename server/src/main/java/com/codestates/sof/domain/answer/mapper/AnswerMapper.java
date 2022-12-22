package com.codestates.sof.domain.answer.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.codestates.sof.domain.answer.dto.AnswerDto;
import com.codestates.sof.domain.answer.entity.Answer;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
	Answer answerPostToAnswer(AnswerDto.Post requestBody);

	Answer answerPatchToAnswer(AnswerDto.Patch requestBody);

	AnswerDto.Response answerToAnswerResponse(Answer answer);

	List<AnswerDto.Response> answersToAnswerResponses(List<Answer> answers);
}
