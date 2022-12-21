package com.codestates.sof.global.utils;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

public class AsciiUtils {
	public static OperationRequestPreprocessor getRequestPreProcessor() {
		return preprocessRequest(prettyPrint());
	}

	public static OperationResponsePreprocessor getResponsePreProcessor() {
		return preprocessResponse(prettyPrint());
	}
}