package com.codestates.sof.domain.tag.mapper;

import org.mapstruct.Mapper;

import com.codestates.sof.domain.question.entity.QuestionTag;
import com.codestates.sof.domain.tag.dto.TagDto;
import com.codestates.sof.domain.tag.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
	TagDto.Response tagToResponse(Tag tag);

	default TagDto.Response map(QuestionTag questionTag) {
		return tagToResponse(questionTag.getTag());
	}
}
