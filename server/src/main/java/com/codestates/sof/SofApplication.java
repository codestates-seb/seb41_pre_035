package com.codestates.sof;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.repository.MemberRepository;
import com.codestates.sof.domain.tag.entity.Tag;
import com.codestates.sof.domain.tag.repository.TagRepository;

@EnableJpaAuditing
@SpringBootApplication
@PropertySource("classpath:/env.properties")
public class SofApplication extends SpringBootServletInitializer {
	// @Autowired
	private MemberRepository memberRepository;

	// @Autowired
	private TagRepository tagRepository;

	// @Autowired
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(SofApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SofApplication.class);
	}

	// @Override
	// public void run(String... args) throws Exception {
	// 	Member member = new Member();
	// 	member.setName("name");
	// 	member.setEmail("email@email.com");
	// 	member.setEncryptedPassword(encoder.encode("password"));
	// 	member.setVerificationFlag(true);
	// 	memberRepository.save(member);
	//
	// 	tagRepository.save(new Tag("tag1", "description1"));
	// 	tagRepository.save(new Tag("tag2", "description2"));
	// }
}
