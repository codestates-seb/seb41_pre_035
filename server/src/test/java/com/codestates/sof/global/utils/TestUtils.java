package com.codestates.sof.global.utils;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import java.util.function.BiFunction;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class TestUtils {
	public static final BiFunction<String, String, MockHttpServletRequestBuilder> POST = (path, body) -> post(path)
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.content(body);
}
