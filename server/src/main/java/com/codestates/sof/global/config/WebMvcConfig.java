package com.codestates.sof.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.codestates.sof.domain.question.page.QuestionPageableHandlerMethodArgumentResolver;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	// @property -> static 제거
	private static final String PAGE_PARAMETER_NAME = "page";
	private static final String SIZE_PARAMETER_NAME = "size";
	private static final String SORT_PARAMETER_NAME = "sort";

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(questionResolver());
	}

	@Bean
	public QuestionPageableHandlerMethodArgumentResolver questionResolver() {
		QuestionPageableHandlerMethodArgumentResolver questionResolver = new QuestionPageableHandlerMethodArgumentResolver();
		questionResolver.setPageParameterName(PAGE_PARAMETER_NAME);
		questionResolver.setSizeParameterName(SIZE_PARAMETER_NAME);
		questionResolver.setSortParameterName(SORT_PARAMETER_NAME);
		return questionResolver;
	}

	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
		//
		return new Jackson2ObjectMapperBuilder()
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
			.visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
			.visibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
	}
}
