package com.codestates.sof.global.error.exception;

import com.codestates.sof.global.error.dto.ExceptionCode;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException {
	@Getter
	private final ExceptionCode exceptionCode;

	public BusinessLogicException(ExceptionCode exceptionCode) {
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
	}

}
