package com.sergio.oauth2.backend.controllers;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.sergio.oauth2.backend.dtos.TokenDTO;
import com.sergio.oauth2.backend.dtos.UrlDTO;

@RestController
public class AuthController {

	@Value("${spring.security.oauth2.resourceserver.opaque-token.clientId}")
	private String clientId;
	
	@Value("${spring.security.oauth2.resourceserver.opaque-token.clientSecret}")
	private String clientSecret;
	
	@GetMapping("/auth/url")
	public ResponseEntity<UrlDTO> auth() {
	String url = new GoogleAuthorizationCodeRequestUrl(
				clientId,
				"http://localhost:4200",
				Arrays.asList("email", "profile")
			).build();
	return ResponseEntity.ok(new UrlDTO(url));
		
	}
	
	@GetMapping("/auth/callback")
	public ResponseEntity<TokenDTO> callback(@RequestParam("code") String code) {
		
		String token;
		try {
			token = new GoogleAuthorizationCodeTokenRequest(
					new NetHttpTransport(),
					new GsonFactory(),
					clientId,
					clientSecret,
					code,
					"http://localhost:4200"
					
			).execute().getAccessToken();
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(new TokenDTO(token));
	}
	
}
