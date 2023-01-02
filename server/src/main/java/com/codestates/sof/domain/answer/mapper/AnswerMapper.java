package com.codestates.sof.domain.answer.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.codestates.sof.domain.answer.dto.AnswerDto;
import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.member.mapper.MemberMapper;
import com.codestates.sof.domain.question.mapper.QuestionMapper;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	uses = {MemberMapper.class, QuestionMapper.class})
public interface AnswerMapper {

	@Mapping(source = "memberId", target = "member.memberId")
	@Mapping(source = "questionId", target = "question.questionId")
	Answer postToAnswer(AnswerDto.Post requestBody);

	@Mapping(source = "memberId", target = "member.memberId")
	Answer patchToAnswer(AnswerDto.Patch requestBody);

	@Mapping(source = "member.memberId", target = "memberId")
	@Mapping(source = "question.questionId", target = "questionId")
	AnswerDto.Response answerToResponse(Answer answer);

	@Mapping(source = "member.memberId", target = "memberId")
	@Mapping(source = "question.questionId", target = "questionId")
	List<AnswerDto.Response> answersToResponses(List<Answer> answers);
}
