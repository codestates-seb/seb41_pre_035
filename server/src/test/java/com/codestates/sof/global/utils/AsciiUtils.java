package com.codestates.sof.global.utils;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.snippet.Snippet;

public class AsciiUtils {
	public static OperationRequestPreprocessor getRequestPreProcessor() {
		return preprocessRequest(prettyPrint());
	}

	public static OperationResponsePreprocessor getResponsePreProcessor() {
		return preprocessResponse(prettyPrint());
	}

	public static RestDocumentationResultHandler getDefaultDocument(String identifier, Snippet... snippets) {
		return document(
			identifier,
			getRequestPreProcessor(),
			getResponsePreProcessor(),
			snippets
		);
	}
}