package com.codestates.sof.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.auth.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	private final AuthService authService;

	@GetMapping("accountVerification/{token}")
	public ResponseEntity<?> verifyAccount(@PathVariable String token) {
		return authService.verifyAccount(token);
	}
}