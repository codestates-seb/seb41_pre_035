package com.codestates.sof.domain.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class RandomPasswordGeneratorTest {
	@Test
	public void generateTest() {
		int numberOfUpperCaseLetters = (int) (Math.random() * 10);
		int numberOfLowerCaseLetters = (int) (Math.random() * 10);
		int numberOfNumeric = (int) (Math.random() * 10);
		int numberOfSpecialChars = (int) (Math.random() * 10);

		String password = RandomPasswordGenerator.generate(
			numberOfUpperCaseLetters,
			numberOfLowerCaseLetters,
			numberOfNumeric,
			numberOfSpecialChars
		);

		// 비밀번호 길이 검증
		int lengthExpected = numberOfUpperCaseLetters + numberOfLowerCaseLetters + numberOfNumeric + numberOfSpecialChars;
		int lengthActual = password.length();
		assertEquals(lengthExpected, lengthActual);

		// '알파벳 대문자’ 개수 검증
		Pattern uppercasePattern = Pattern.compile("[A-Z]");
		int uppercaseActual = regexCount(password, uppercasePattern);
		assertEquals(numberOfUpperCaseLetters, uppercaseActual);

		// '알파벳 소문자’ 개수 검증
		Pattern lowercasePattern = Pattern.compile("[a-z]");
		int lowercaseActual = regexCount(password, lowercasePattern);
		assertEquals(numberOfLowerCaseLetters, lowercaseActual);

		// ‘0 이상인 숫자’ 개수 검증
		Pattern numericPattern = Pattern.compile("\\d");
		int numericActual = regexCount(password, numericPattern);
		assertEquals(numberOfNumeric, numericActual);

		// '특수문자’ 개수 검증
		Pattern specialCharsPattern = Pattern.compile("\\W");
		int specialCharsActual = regexCount(password, specialCharsPattern);
		assertEquals(specialCharsActual, specialCharsActual);
	}

	public int regexCount(String str, Pattern pattern) {
		Matcher matcher = pattern.matcher(str);
		int count = 0;
		while (matcher.find()) count += 1;

		return count;
	}
}