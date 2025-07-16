package com.sergio.oauth2.backend.controllers;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.oauth2.backend.dtos.MessageDTO;

@RestController
public class PrivateController {

	
	@GetMapping("/messages")
	public ResponseEntity<MessageDTO> privateMessages(
			@AuthenticationPrincipal(expression = "name")  String name,
//			@AuthenticationPrincipal(expression = "email")  String email,
			@AuthenticationPrincipal OAuth2AuthenticatedPrincipal test
			) {
		
//		System.out.println(principal.toString());
		return ResponseEntity.ok(new MessageDTO("Private content " + name + " Email: " + test.getAttribute("email")));
		
	}
}

