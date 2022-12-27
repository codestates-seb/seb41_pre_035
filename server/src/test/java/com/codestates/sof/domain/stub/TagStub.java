package com.codestates.sof.domain.stub;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.codestates.sof.domain.tag.dto.TagDto;
import com.codestates.sof.domain.tag.entity.Tag;

public class TagStub {
	public static List<Tag> defaultTags() {
		return List.of(
			Tag.Builder.aTag()
				.name("java")
				.description("Java is a high-level object-oriented programming language.")
				.build(),
			Tag.Builder.aTag()
				.name("javascript")
				.description(
					"For questions about programming in ECMAScript (JavaScript/JS) and its different dialects/implementations (except for ActionScript).")
				.build(),
			Tag.Builder.aTag()
				.name("python")
				.description("Python is a multi-paradigm, dynamically typed, multi-purpose programming language.")
				.build()
		);
	}

	public static List<TagDto.Response> simpleResponses() {
		List<String> tags = List.of("java", "javascript", "python");

		return IntStream.range(0, tags.size())
			.mapToObj(i -> {
				TagDto.Response response = new TagDto.Response();
				response.setTagId((long)i);
				response.setName(tags.get(i));
				return response;
			}).collect(Collectors.toList());
	}

	public static List<TagDto.Response> responses() {
		List<String> tags = List.of("java", "javascript", "python");
		List<String> desc = List.of(
			"Java is a high-level object-oriented programming language.",
			"For questions about programming in ECMAScript (JavaScript/JS) and its different dialects/implementations (except for ActionScript).",
			"Python is a multi-paradigm, dynamically typed, multi-purpose programming language."
		);

		return IntStream.range(0, tags.size())
			.mapToObj(i -> {
				TagDto.Response response = new TagDto.Response();
				response.setTagId((long)i);
				response.setName(tags.get(i));
				response.setDescription(desc.get(i));
				response.setTaggedCount((long)i);
				return response;
			}).collect(Collectors.toList());
	}
}
