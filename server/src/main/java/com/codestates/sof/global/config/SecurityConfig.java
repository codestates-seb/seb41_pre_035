package com.codestates.sof.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.codestates.sof.domain.auth.filter.JwtAuthenticationFilter;
import com.codestates.sof.domain.auth.filter.JwtVerificationFilter;
import com.codestates.sof.domain.auth.handler.MemberAuthenticationEntryPoint;
import com.codestates.sof.domain.auth.handler.MemberAuthenticationFailureHandler;
import com.codestates.sof.domain.auth.handler.MemberAuthenticationSuccessHandler;
import com.codestates.sof.domain.auth.jwt.JwtTokenizer;
import com.codestates.sof.domain.auth.mapper.LoginMapper;
import com.codestates.sof.domain.auth.service.MemberDetailsService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
	private final MemberDetailsService detailsService;
	private final JwtTokenizer jwtTokenizer;
	private final LoginMapper mapper;
	private final RedisTemplate<String, String> redisTemplate;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		// TODO : 필요한 Security 설정을 더 구체화 해야합니다.
		httpSecurity.headers().frameOptions().sameOrigin()
			.and()
			.csrf().disable()
			.cors()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.exceptionHandling()
			.authenticationEntryPoint(new MemberAuthenticationEntryPoint())
			.and()
			.apply(new CustomFilterConfigure())
			.and()
			.userDetailsService(detailsService)
			.authorizeHttpRequests(a -> a
				.antMatchers("/docs/index.html").permitAll()
				.antMatchers("/auth/**", "/h2/**").permitAll()
				.antMatchers(HttpMethod.POST, "/members").permitAll()
				.antMatchers(HttpMethod.GET, "/tags", "/questions", "/answers").permitAll()
				.antMatchers(HttpMethod.GET, "/tags/**", "/questions/**", "/answers/**").permitAll()
				.anyRequest().authenticated());  // TODO : 도메인 권한 설정 무조건 필요합니다!

		return httpSecurity.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// TODO : CORS 도메인 설정이 필요합니다.
		configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://localhost:3000"));
		configuration.setAllowedMethods(List.of("POST", "GET", "PATCH", "DELETE", "OPTIONS", "HEAD"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(List.of("Authorization", "Refresh"));  // https://ahndding.tistory.com/4

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	private class CustomFilterConfigure extends AbstractHttpConfigurer<CustomFilterConfigure, HttpSecurity> {
		@Override
		public void configure(HttpSecurity httpSecurity) throws Exception {
			AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);

			JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager,
				jwtTokenizer, redisTemplate);
			jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
			jwtAuthenticationFilter.setAuthenticationSuccessHandler(
				new MemberAuthenticationSuccessHandler(mapper));
			jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());
			httpSecurity.addFilter(jwtAuthenticationFilter);

			JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(detailsService, jwtTokenizer,
				redisTemplate);

			httpSecurity
				.addFilter(jwtAuthenticationFilter)
				.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
		}
	}
}
