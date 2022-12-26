package com.codestates.sof.domain.member.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.codestates.sof.domain.common.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "MEMBERS")
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long memberId;

	@Column(nullable = false, updatable = false, unique = true)
	private String email;

	@Column(nullable = false, length = 127)
	private String name;

	@Column(nullable = false, length = 63)
	private String encryptedPassword;

	@Column(length = 63)
	private String beforeEncryptedPassword;

	@Column(nullable = false)
	private boolean verificationFlag = false;

	@Column(nullable = false)
	private boolean deleteFlag = false;

	@Column(nullable = false)
	private LocalDateTime lastActivateAt = LocalDateTime.now();

	// Profile
	@OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST)
	private Profile profile;

	public void setProfile(Profile profile) {
		this.profile = profile;
		if (profile != null && profile.getMember() != this) {
			profile.setMember(this);
		}
	}
}