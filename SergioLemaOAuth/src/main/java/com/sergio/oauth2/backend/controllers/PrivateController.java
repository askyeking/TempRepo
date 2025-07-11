package com.sergio.oauth2.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.oauth2.backend.dtos.MessageDTO;

@RestController
public class PrivateController {

	
	@GetMapping("/messages")
	public ResponseEntity<MessageDTO> privateMessages(
			@AuthenticationPrincipal(expression = "name")  String name) {
		return ResponseEntity.ok(new MessageDTO("Private content " + name));
		
	}
}

