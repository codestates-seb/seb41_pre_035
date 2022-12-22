package com.codestates.sof.domain.member.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.codestates.sof.domain.member.dto.MemberDto;
import com.codestates.sof.domain.member.entity.Member;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {
	@Mapping(source = "password", target = "encryptedPassword")
	Member memberPostDtoToMember(MemberDto.Post requestBody);

	MemberDto.Response memberToMemberResponseDto(Member member);

	List<MemberDto.Response> memberToMemberResponseDto(List<Member> members);
}
