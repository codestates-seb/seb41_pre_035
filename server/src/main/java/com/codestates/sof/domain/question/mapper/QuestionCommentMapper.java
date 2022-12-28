package com.codestates.sof.domain.question.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.sof.domain.member.mapper.MemberMapper;
import com.codestates.sof.domain.question.dto.QuestionCommentRequestDto;
import com.codestates.sof.domain.question.dto.QuestionCommentResponseDto;
import com.codestates.sof.domain.question.entity.QuestionComment;

@Mapper(componentModel = "spring", uses = {MemberMapper.class})
public interface QuestionCommentMapper {
	QuestionComment postToQuestionComment(QuestionCommentRequestDto.Post post);

	QuestionComment patchToQuestionComment(QuestionCommentRequestDto.Patch patch);

	@Mapping(source = "question.questionId", target = "questionId")
	QuestionCommentResponseDto.Response commentToResponse(QuestionComment questionComment);

	@Mapping(source = "member.name", target = "memberName")
	@Mapping(source = "member.memberId", target = "memberId")
	@Mapping(source = "question.questionId", target = "questionId")
	QuestionCommentResponseDto.SimpleResponse commentToSimpleResponse(QuestionComment questionComment);
}
