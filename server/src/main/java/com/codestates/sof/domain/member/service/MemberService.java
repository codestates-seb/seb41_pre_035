package com.codestates.sof.domain.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public Member createMember(Member member) {
		verifyUserExist(member.getEmail());

		// password 암호화 해야합니다.

		Member saveMember = memberRepository.save(member);

		// member가 생성되었으면 메일 인증 코드를 구현해야합니다.

		return saveMember;
	}

	private void verifyUserExist(String email) {
		Optional<Member> optionalMember = memberRepository.findByEmail(email);
		if (optionalMember.isPresent()) throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
	}
}
