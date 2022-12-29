package com.codestates.sof.domain.auth.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.codestates.sof.domain.member.entity.Member;

public class MemberDetails extends Member implements UserDetails {
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
