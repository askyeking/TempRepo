package com.sergio.oauth2.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.oauth2.backend.dtos.MessageDTO;

@RestController
public class PublicController {

	
	@GetMapping("/public/messages")
	public ResponseEntity<MessageDTO> privateMessages() {
		return ResponseEntity.ok(new MessageDTO("Public content"));
	}
}

