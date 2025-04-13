package com.xpert.controller;

import com.xpert.dto.ChatDTO;
import com.xpert.dto.CreateChatRequestDTO;
import com.xpert.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

	
	private final ChatService chatService;

	 /**
     * Creates a new chat.
     *
     * @param dto the chat request DTO
     * @return the created chat
     */
    @PostMapping
    public ResponseEntity<ChatDTO> createChat(@Valid @RequestBody CreateChatRequestDTO dto) {
        return ResponseEntity.ok(chatService.createChat(dto));
    }

    /**
     * Retrieves all chats for a given user.
     *
     * @param userId the user's ID
     * @return list of chat DTOs
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatDTO>> getChatsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(chatService.getChatsForUser(userId));
    }
}
