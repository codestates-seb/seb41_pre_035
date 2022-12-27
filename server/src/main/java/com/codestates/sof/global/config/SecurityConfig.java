package com.codestates.sof.global.config;

import static org.springframework.security.config.Customizer.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.codestates.sof.domain.auth.filter.JwtAuthenticationFilter;
import com.codestates.sof.domain.auth.handler.MemberAuthenticationFailureHandler;
import com.codestates.sof.domain.auth.handler.MemberAuthenticationSuccessHandler;
import com.codestates.sof.domain.auth.jwt.JwtTokenizer;
import com.codestates.sof.domain.auth.mapper.LoginMapper;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
	private final JwtTokenizer jwtTokenizer;
	private final LoginMapper mapper;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		// TODO : 필요한 Security 설정을 더 구체화 해야합니다.
		httpSecurity.headers().frameOptions().sameOrigin()
			.and()
			.csrf().disable()
			.cors(withDefaults())
			.formLogin().disable()
			.httpBasic().disable()
			.apply(new CustomFilterConfigure())
			.and()
			.authorizeHttpRequests(a -> a
				.antMatchers("/auth/**", "/h2/**").permitAll()
				.antMatchers(HttpMethod.POST, "/members").permitAll()
				.anyRequest().permitAll());

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
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("POST", "GET", "PATCH", "DELETE", "OPTION"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	private class CustomFilterConfigure extends AbstractHttpConfigurer<CustomFilterConfigure, HttpSecurity> {
		@Override
		public void configure(HttpSecurity httpSecurity) {
			AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);

			JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager,
				jwtTokenizer);

			jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
			jwtAuthenticationFilter.setAuthenticationSuccessHandler(
				new MemberAuthenticationSuccessHandler(mapper));
			jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());
			httpSecurity.addFilter(jwtAuthenticationFilter);
		}
	}
}