package com.xpert.controller;

import com.xpert.dto.ChatMessageDTO;
import com.xpert.dto.SendMessageRequestDTO;
import com.xpert.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing chat messages.
 */

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

   
    private final MessageService messageService;

    /**
     * Sends a new message in a chat.
     *
     * @param dto the message send request
     * @return the created ChatMessageDTO
     */
    @PostMapping
    public ResponseEntity<ChatMessageDTO> sendMessage(@Valid @RequestBody SendMessageRequestDTO dto) {
        ChatMessageDTO messageDTO = messageService.sendMessage(dto);
        return ResponseEntity.ok(messageDTO);
    }

    /**
     * Retrieves the message history for a specific chat.
     *
     * @param chatId the ID of the chat
     * @return list of ChatMessageDTOs
     */
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesByChat(@PathVariable Long chatId) {
        List<ChatMessageDTO> messages = messageService.getMessagesByChatId(chatId);
        return ResponseEntity.ok(messages);
    }
}
