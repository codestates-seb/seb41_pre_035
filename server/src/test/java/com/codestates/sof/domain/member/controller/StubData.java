package com.codestates.sof.domain.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codestates.sof.domain.member.dto.MemberDto;
import com.codestates.sof.domain.member.dto.ProfileDto;
import com.codestates.sof.domain.member.entity.Member;

public class StubData {
	private static final Map<HttpMethod, Object> stubRequestBody;

	static {
		stubRequestBody = new HashMap<>();
		stubRequestBody.put(HttpMethod.POST, new MemberDto.Post("user01@hello.com", "1111", "user01"));
		stubRequestBody.put(HttpMethod.PATCH, new MemberDto.Patch(
			0,
			"user01",
			null,
			new ProfileDto(
				"My profile",
				"Hello world!",
				"Seoul, South Korea",
				"websiteLink",
				"twitter",
				"github")));
	}

	public static class MockMember {
		public static Object getRequestBody(HttpMethod method) {
			return stubRequestBody.get(method);
		}

		public static Page<Member> getMemberPage() {
			int page = 1;
			int size = 15;
			List<Member> members = new ArrayList<>();
			for (int i = 1; i <= 3; i++) {
				Member member = new Member();
				member.setMemberId(i);
				member.setEncryptedPassword(String.format("%04d", i));
				member.setEmail(String.format("user%02d@hello.com", i));
				member.setName(String.format("user%02d", i));

				members.add(member);
			}

			return new PageImpl<>(members, PageRequest.of(page - 1, size,
				Sort.by("memberId").descending()), members.size());
		}

		public static MemberDto.Response getSingleResponseBody() {
			return new MemberDto.Response(
				1L,
				"user01@hello.com",
				"user01",
				false,
				false,
				LocalDateTime.now(),
				new ProfileDto(
					"My profile",
					"Hello world!",
					"Seoul, South Korea",
					"websiteLink",
					"twitter",
					"github"));
		}

		public static List<MemberDto.Response> getMultiResponseBody() {
			List<MemberDto.Response> responses = new ArrayList<>();
			for (int i = 1; i <= 3; i++) {
				MemberDto.Response member = new MemberDto.Response(
					i,
					String.format("user%02d@hello.com", i),
					String.format("user%02d", i),
					false,
					false,
					LocalDateTime.now(),
					new ProfileDto(
						"My profile",
						"Hello world!",
						"Seoul, South Korea",
						"websiteLink",
						"twitter",
						"github")
				);

				responses.add(member);
			}
			return responses;
		}
	}
}