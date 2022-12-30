package com.codestates.sof.domain.answer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.codestates.sof.domain.answer.dto.AnswerVoteDto;
import com.codestates.sof.domain.answer.entity.AnswerVote;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	uses = AnswerMapper.class)
public interface AnswerVoteMapper {

	AnswerVote patchToAnswerVote(AnswerVoteDto.Patch requestBody);

	@Mapping(source = "answer.answerId", target = "answerId")
	AnswerVoteDto.Response answerVoteToResponse(AnswerVote answerVote);
}
