package com.xpert.service;

import com.xpert.dto.ChatMessageDTO;
import com.xpert.dto.SendMessageRequestDTO;

import java.util.List;

public interface MessageService {

    // Send a new message to a chat
    ChatMessageDTO sendMessage(SendMessageRequestDTO dto);

    // Get all messages for a chat
    List<ChatMessageDTO> getMessagesByChatId(Long chatId);
}
