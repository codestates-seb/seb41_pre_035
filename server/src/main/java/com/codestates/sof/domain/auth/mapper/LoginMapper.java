package com.codestates.sof.domain.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.codestates.sof.domain.auth.dto.LoginDto;
import com.codestates.sof.domain.member.entity.Member;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LoginMapper {
	LoginDto.Response loginMemberToLoginResponseDto(Member member);
}
