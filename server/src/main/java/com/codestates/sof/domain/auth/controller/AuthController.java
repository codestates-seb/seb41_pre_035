package com.codestates.sof.domain.auth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.auth.entity.VerificationToken;
import com.codestates.sof.domain.auth.service.AuthService;
import com.codestates.sof.domain.auth.service.EmailService;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.service.MemberService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	private final EmailService emailService;
	private final AuthService authService;
	private final MemberService memberService;

	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<?> verifyAccount(@PathVariable("token") String token) {
		return authService.verifyAccount(token);
	}

	@GetMapping("/password")
	public ResponseEntity<?> forgetPassword(@RequestBody Map<String,String> map) {
		String email = map.get("email");

		Member member = memberService.findMemberByEmail(email);
		String token = authService.generateToken(member, 60, VerificationToken.tokenType.PASSWORD_RESET);
		emailService.sendPasswordResetVerifyEmail(member, token);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/password/{token}")
	public ResponseEntity<?> resetPassword(@PathVariable("token") String token) {
		authService.resetPassword(token);

		return new ResponseEntity<>("Password reset Successfully. Please check your email", HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		authService.logout(request);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
