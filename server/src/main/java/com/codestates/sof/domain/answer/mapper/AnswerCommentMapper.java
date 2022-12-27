package com.codestates.sof.domain.answer.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.codestates.sof.domain.answer.dto.AnswerCommentDto;
import com.codestates.sof.domain.answer.entity.AnswerComment;
import com.codestates.sof.domain.member.mapper.MemberMapper;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	uses = MemberMapper.class)
public interface AnswerCommentMapper {

	@Mapping(source = "memberId", target = "member.memberId")
	AnswerComment postToComment(AnswerCommentDto.Post requestBody);

	@Mapping(source = "memberId", target = "member.memberId")
	AnswerComment patchToComment(AnswerCommentDto.Patch requestBody);

	@Mapping(source = "answer.answerId", target = "answerId")
	AnswerCommentDto.Response commentToResponse(AnswerComment answerComment);

	List<AnswerCommentDto.Response> commentsToResponses(List<AnswerComment> answerComments);
}
