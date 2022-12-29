package com.codestates.sof.domain.common;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.codestates.sof.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Inheritance 사용안한이유: https://ict-nroo.tistory.com/128

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Vote extends BaseEntity {
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;

	@Enumerated(EnumType.STRING)
	@Column(name = "vote_type", nullable = false)
	private VoteType type = VoteType.NONE;

	public Vote(Member member, VoteType type) {
		this.member = member;
		this.type = type;
	}
<<<<<<< HEAD
=======

	public void modify(VoteType type) {
		this.type = type;
	}
>>>>>>> a9fa0e2d90b300552a108513091169a6d873aa20
}
