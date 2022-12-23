package com.codestates.sof.domain.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codestates.sof.domain.member.entity.Member;


@SpringBootTest
class CustomBeanUtilsTest {
	@Autowired
	private CustomBeanUtils<Member> beanUtils;

	@Test
	public void copyNonNullPropertiesTest() {
		// given
		Member src = new Member();
		src.setName("toki");

		Member dst = new Member();
		dst.setEncryptedPassword("1111");
		dst.setName("roki");

		// when
		beanUtils.copyNonNullProperties(src, dst);

		//then
		assertEquals(dst.getName(), src.getName());
		assertEquals("1111", dst.getEncryptedPassword());

	}

}