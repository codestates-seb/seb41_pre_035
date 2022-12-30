package com.codestates.sof;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.codestates.sof.domain.tag.entity.Tag;
import com.codestates.sof.domain.tag.repository.TagRepository;

@EnableJpaAuditing
@SpringBootApplication
@PropertySource("classpath:/env.properties")
public class SofApplication extends SpringBootServletInitializer implements CommandLineRunner {
	@Autowired
	private TagRepository tagRepository;

	public static void main(String[] args) {
		SpringApplication.run(SofApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SofApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		IntStream.range(1, 100)
			.mapToObj(i -> new Tag(String.format("tag%d", i), String.format("description-%d", i)))
			.forEach(tagRepository::save);
	}
}
