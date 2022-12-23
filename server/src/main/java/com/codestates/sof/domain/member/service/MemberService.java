package com.codestates.sof.domain.member.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

	public Page<Member> findAllMember(int page, int size) {
		// TODO: 사용자를 불러오는 도메인이 Users페이지일텐데, 기본 정렬이 어떤 것인지 구체적으로 협의된 바가 없어
		//  mamberId 기준으로 내림차순 정렬(=최신순)해서 리턴합니다.
		return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
	}

	public Member findMember(long memberId) {
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_MEMBER));

		return member;

	}

	private void verifyUserExist(String email) {
		Optional<Member> optionalMember = memberRepository.findByEmail(email);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
		}
	}

}
