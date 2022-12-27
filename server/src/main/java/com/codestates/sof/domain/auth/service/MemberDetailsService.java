package com.codestates.sof.domain.auth.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MemberDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optionalMember = memberRepository.findByEmail(username);
		Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_MEMBER));

		return new MemberDetails(member);
	}

	private static class MemberDetails extends Member implements UserDetails {
		public MemberDetails(Member member) {
			setMemberId(member.getMemberId());
			setEmail(member.getEmail());
			setName(member.getName());
			setEncryptedPassword(member.getEncryptedPassword());
			setVerificationFlag(member.getVerificationFlag());
			setDeleteFlag(member.getDeleteFlag());
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return null;
		}

		@Override
		public String getPassword() {
			return getEncryptedPassword();
		}

		@Override
		public String getUsername() {
			return getEmail();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return getVerificationFlag();  // 회원의 이메일 인증 여부 확인
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return !getDeleteFlag();  // 회원의 탈퇴 여부 확인
		}
	}
}