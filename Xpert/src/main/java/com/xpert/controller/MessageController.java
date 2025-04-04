package com.xpert.controller;

import com.xpert.dto.ChatMessageDTO;
import com.xpert.dto.SendMessageRequestDTO;
import com.xpert.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Send a new message
    @PostMapping
    public ResponseEntity<ChatMessageDTO> sendMessage(@Valid @RequestBody SendMessageRequestDTO dto) {
        ChatMessageDTO messageDTO = messageService.sendMessage(dto);
        return ResponseEntity.ok(messageDTO);
    }

    // Get message history for a chat
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesByChat(@PathVariable Long chatId) {
        List<ChatMessageDTO> messages = messageService.getMessagesByChatId(chatId);
        return ResponseEntity.ok(messages);
    }
}
