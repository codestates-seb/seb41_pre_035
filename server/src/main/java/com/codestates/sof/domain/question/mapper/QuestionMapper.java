package com.codestates.sof.domain.question.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.sof.domain.answer.mapper.AnswerMapper;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.mapper.MemberMapper;
import com.codestates.sof.domain.question.dto.QuestionDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.tag.mapper.TagMapper;

@Mapper(componentModel = "spring", uses = {MemberMapper.class, TagMapper.class, AnswerMapper.class})
public interface QuestionMapper {
	@Mapping(target = "writer", source = "member")
	@Mapping(target = "isItWriter", expression = "java(question.isItWriter())")
	@Mapping(target = "hasAlreadyVoted", expression = "java(question.hasAlreadyVoted())")
	QuestionDto.Response questionToResponse(Question question);

	default Member memberIdToMember(Long memberId) {
		Member member = new Member();
		member.setMemberId(memberId);
		return member;
	}

	default Question postToQuestion(QuestionDto.Post post) {
		Question question = new Question(memberIdToMember(post.getWriterId()), post.getTitle(), post.getContent());
		post.getTags().forEach(question::addTag);
		return question;
	}

	default Question patchToQuestion(QuestionDto.Patch patch) {
		Question question = new Question(null, patch.getTitle(), patch.getContent());
		patch.getTags().forEach(question::addTag);
		return question;
	}
}
