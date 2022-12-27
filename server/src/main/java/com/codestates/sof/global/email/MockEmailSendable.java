package com.codestates.sof.global.email;

import java.util.Map;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class MockEmailSendable implements EmailSendable {
	private final MemberRepository memberRepository;

	@Override
	public void send(String[] to, String subject, Map<String, Object> variables, String templateName) {
		for (String t : to) {
			log.info("# Send a mock email to : " + t);

			// 이메일 인증을 했다고 가정합니다.
			Member member = memberRepository.findByEmail(t).orElseThrow(() ->
				new BusinessLogicException(ExceptionCode.NOT_FOUND_MEMBER)
			);
			member.setVerificationFlag(true);
			memberRepository.save(member);
		}
	}
}