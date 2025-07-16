package com.sergio.oauth2.backend.controllers;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
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
	
	@Autowired
	private OpaqueTokenIntrospector googleIntrospector;
	
	
	//Endpoint that returns a valid google OAuth URL
	@GetMapping("/auth/url")
	public ResponseEntity<UrlDTO> auth() {
		
		String url = new GoogleAuthorizationCodeRequestUrl(
					clientId,
	//				"http://localhost:4200/#/test",
					"http://localhost:4200",
					Arrays.asList("email", "profile")
				).build();
	
		return ResponseEntity.ok(new UrlDTO(url));
		
	}
	
	//Exchange code for token
	@GetMapping("/auth/getToken")
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
			
			OAuth2AuthenticatedPrincipal principal = googleIntrospector.introspect(token);
			System.out.println("********");
			System.out.println(principal.getName());
			System.out.println(principal.getAttribute("name") + "");
			System.out.println("********");
			System.out.println(principal.getAttribute("email") + "");
			
			//When exchanging code for token
			//call introspect method, passing token, to get user data
			//check database if user data exists
				//if not, persist them
				//if yes, don't persist
					//if yes, but signed in with facebook but was google previously, update user info
							//get info from google
							//look for accounts in database with facebook data identical to data read from google
								//if match was found, ask user if it is them
									//if yes, ask them to authenticate through facebook to prove it.
			
			
			
			
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(new TokenDTO(token));
	}
	
}
