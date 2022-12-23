package com.codestates.sof.domain.question.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.codestates.sof.domain.answer.mapper.AnswerMapper;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.mapper.MemberMapper;
import com.codestates.sof.domain.question.dto.QuestionDto;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionTag;
import com.codestates.sof.domain.tag.entity.Tag;
import com.codestates.sof.domain.tag.mapper.TagMapper;

@Mapper(componentModel = "spring", uses = {MemberMapper.class, TagMapper.class, AnswerMapper.class})
public interface QuestionMapper {
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
		question.setTags(tagNamesToQuestionTags(question, post.getTags()));
		return question;
	}

	default Question patchToQuestion(QuestionDto.Patch patch) {
		Question question = new Question(null, patch.getTitle(), patch.getContent());
		question.setTags(tagNamesToQuestionTags(question, patch.getTags()));
		return question;
	}

	default List<QuestionTag> tagNamesToQuestionTags(Question question, List<String> tagNames) {
		return tagNames.stream()
			.map(tagName -> tagNameToQuestionTag(question, tagName))
			.collect(Collectors.toList());
	}

	default QuestionTag tagNameToQuestionTag(Question question, String tagName) {
		QuestionTag tag = new QuestionTag();
		tag.setTag(new Tag(tagName));
		tag.setQuestion(question);
		return tag;
	}
}
