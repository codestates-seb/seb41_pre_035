package com.codestates.sof.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;

public class ExceptionCodeSnippet extends AbstractFieldsSnippet {
	public ExceptionCodeSnippet(
		String type,
		List<FieldDescriptor> descriptors,
		Map<String, Object> attributes, boolean ignoreUndocumentedFields) {
		super(type, descriptors, attributes, ignoreUndocumentedFields);
	}

	public ExceptionCodeSnippet(String name, String type, List<FieldDescriptor> descriptors,
		Map<String, Object> attributes, boolean ignoreUndocumentedFields) {
		super(name, type, descriptors, attributes, ignoreUndocumentedFields);
	}

	@Override
	protected MediaType getContentType(Operation operation) {
		return operation.getResponse().getHeaders().getContentType();
	}

	@Override
	protected byte[] getContent(Operation operation) throws IOException {
		return operation.getResponse().getContent();
	}
}
