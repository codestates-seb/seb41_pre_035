package com.codestates.sof.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileDto {
	private String title;
	private String aboutMe;
	private String location;
	private String websiteLink;
	private String twitterLink;
	private String githubLink;
}