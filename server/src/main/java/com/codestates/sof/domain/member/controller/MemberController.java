package com.codestates.sof.domain.member.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.member.dto.MemberDto;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.mapper.MemberMapper;
import com.codestates.sof.domain.member.service.MemberService;
import com.codestates.sof.global.dto.MultiResponseDto;
import com.codestates.sof.global.dto.SingleResponseDto;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/members")
@AllArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final MemberMapper mapper;

	@PostMapping
	public ResponseEntity<?> postMember(@RequestBody MemberDto.Post requestBody) {
		Member postDtoToMember = mapper.memberPostDtoToMember(requestBody);
		Member member = memberService.createMember(postDtoToMember);
		MemberDto.Response response = mapper.memberToMemberResponseDto(member);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getMembers(@RequestParam int page, @RequestParam int size) {
		Page<Member> memberPage = memberService.findAllMember(page - 1, size);
		List<Member> members = memberPage.getContent();
		List<MemberDto.Response> responses = mapper.memberToMemberResponseDto(members);

		return new ResponseEntity<>(new MultiResponseDto<>(responses, memberPage), HttpStatus.OK);
	}

	@GetMapping("/{member-id}")
	public ResponseEntity<?> getMember(@PathVariable("member-id") long memberId) {
		Member member = memberService.findMember(memberId);
		MemberDto.Response response = mapper.memberToMemberResponseDto(member);

		return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
	}
}