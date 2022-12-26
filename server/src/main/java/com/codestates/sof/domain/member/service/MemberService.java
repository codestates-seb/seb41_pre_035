package com.codestates.sof.domain.member.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codestates.sof.domain.auth.service.AuthService;
import com.codestates.sof.domain.common.CustomBeanUtils;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.entity.Profile;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final CustomBeanUtils<Member> memberCustomBeanUtils;
	private final CustomBeanUtils<Profile> profileCustomBeanUtils;
	private final PasswordEncoder passwordEncoder;
	private final AuthService authService;

	public Member createMember(Member member) {
		verifyUserExist(member.getEmail());

		String encode = passwordEncoder.encode(member.getEncryptedPassword());
		member.setEncryptedPassword(encode);
		System.out.println(encode);
		Member saveMember = memberRepository.save(member);

		authService.sendActivationEmail(saveMember);

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

	public Member updateMember(Member member) {
		Member findMember = findMember(member.getMemberId());

		// Profile의 변경사항이 있으면 Profile 객체 먼저 업데이트 후 업데이트 된 Profile 객체를 Member에 업데이트 해야합니다.
		if (member.getProfile() != null) {
			profileCustomBeanUtils.copyNonNullProperties(member.getProfile(), findMember.getProfile());
			member.setProfile(findMember.getProfile());
		}

		Optional.ofNullable(member.getEncryptedPassword()).ifPresent(newPassword -> {
			member.setBeforeEncryptedPassword(findMember.getEncryptedPassword());
			member.setEncryptedPassword(passwordEncoder.encode(newPassword));
		});
		memberCustomBeanUtils.copyNonNullProperties(member, findMember);

		return memberRepository.save(findMember);
	}

	public void deleteMember(long memberId) {
		Member member = findMember(memberId);
		member.setDeleteFlag(true);
		memberRepository.save(member);
	}

	private void verifyUserExist(String email) {
		Optional<Member> optionalMember = memberRepository.findByEmail(email);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
		}
	}

}
