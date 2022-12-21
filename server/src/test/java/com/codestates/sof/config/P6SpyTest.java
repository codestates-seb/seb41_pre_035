package com.codestates.sof.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.codestates.sof.domain.member.Member;

@DataJpaTest
@EnableQueryLog
@ActiveProfiles("local")
public class P6SpyTest {
	@Autowired
	TestEntityManager em;

	@Test
	void test() {
		Member member = new Member();
		member.setName("MemberA");

		member = em.persistAndFlush(member);

		em.clear();

		Member findMember = em.find(Member.class, member.getId());
	}
}
