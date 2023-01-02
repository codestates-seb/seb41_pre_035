package com.codestates.sof.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.codestates.sof.domain.question.support.QuestionPageableArgumentResolver;
import com.codestates.sof.domain.tag.support.TagPageableArgumentResolver;
import com.codestates.sof.global.config.support.PageableHandlerMethodargumentResolver;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	// @property 사용시 static 제거
	private static final String PAGE_PARAMETER_NAME = "page";
	private static final String SIZE_PARAMETER_NAME = "size";
	private static final String SORT_PARAMETER_NAME = "sort";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/docs/**").addResourceLocations("classpath:/static/docs/");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(questionPageableArgumentResolver());
		resolvers.add(tagPageableArgumentResolver());
	}

	@Bean
	public QuestionPageableArgumentResolver questionPageableArgumentResolver() {
		QuestionPageableArgumentResolver questionResolver = new QuestionPageableArgumentResolver(1, 5);
		setDefaultParameterNames(questionResolver);
		return questionResolver;
	}

	@Bean
	public TagPageableArgumentResolver tagPageableArgumentResolver() {
		TagPageableArgumentResolver tagResolver = new TagPageableArgumentResolver(1, 30);
		setDefaultParameterNames(tagResolver);
		return tagResolver;
	}

	private void setDefaultParameterNames(PageableHandlerMethodargumentResolver<?> resolver) {
		resolver.setPageParameterName(PAGE_PARAMETER_NAME);
		resolver.setSizeParameterName(SIZE_PARAMETER_NAME);
		resolver.setSortParameterName(SORT_PARAMETER_NAME);
	}

	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
		return new Jackson2ObjectMapperBuilder()
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
			.visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
			.visibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
	}
}
