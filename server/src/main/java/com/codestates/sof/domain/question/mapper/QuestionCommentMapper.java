package com.codestates.sof.domain.question.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.sof.domain.member.mapper.MemberMapper;
import com.codestates.sof.domain.question.dto.QuestionCommentDto;
import com.codestates.sof.domain.question.entity.QuestionComment;

@Mapper(componentModel = "spring", uses = {MemberMapper.class})
public interface QuestionCommentMapper {
	@Mapping(source = "memberId", target = "member.memberId")
	@Mapping(source = "questionId", target = "question.questionId")
	QuestionComment postToQuestionComment(QuestionCommentDto.Post post);

	@Mapping(source = "commentId", target = "questionCommentId")
	@Mapping(source = "modifierId", target = "member.memberId")
	@Mapping(source = "questionId", target = "question.questionId")
	QuestionComment patchToQuestionComment(QuestionCommentDto.Patch patch);

	@Mapping(source = "question.questionId", target = "questionId")
	QuestionCommentDto.Response questionCommentToResponse(QuestionComment questionComment);
}
