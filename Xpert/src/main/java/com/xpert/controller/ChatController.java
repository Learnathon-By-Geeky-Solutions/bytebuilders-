package com.xpert.controller;

import com.xpert.dto.ChatDTO;
import com.xpert.dto.CreateChatRequestDTO;
import com.xpert.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

	@Autowired
	private ChatService chatService;

	// Create a new chat
	@PostMapping
	public ResponseEntity<ChatDTO> createChat(@Valid @RequestBody CreateChatRequestDTO dto) {
		ChatDTO chatDTO = chatService.createChat(dto);
		return ResponseEntity.ok(chatDTO);
	}

	// Get all chats for a specific user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<ChatDTO>> getChatsForUser(@PathVariable Long userId) {
		List<ChatDTO> chats = chatService.getChatsForUser(userId);
		return ResponseEntity.ok(chats);
	}
}
