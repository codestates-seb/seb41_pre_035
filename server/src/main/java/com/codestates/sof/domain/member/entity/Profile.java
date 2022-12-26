package com.codestates.sof.domain.member.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "PROFILES")
public class Profile {
	@Id
	@Column(name = "MEMBER_ID", nullable = false)
	private long id;

	// Member
	@OneToOne(cascade = CascadeType.DETACH)
	@MapsId
	@JoinColumn(nullable = false, name = "MEMBER_ID")
	private Member member;

	public void setMember(Member member) {
		this.member = member;
		if (member.getProfile() != this) {
			member.setProfile(this);
		}
	}

	private String title;

	@Column(columnDefinition = "MEDIUMTEXT")
	private String aboutMe;

	private String location;

	private String websiteLink;

	private String twitterLink;

	private String githubLink;
}