package com.xpert.service;

import com.xpert.dto.ChatDTO;
import com.xpert.dto.CreateChatRequestDTO;

import java.util.List;

public interface ChatService {

    // Create a new chat
    ChatDTO createChat(CreateChatRequestDTO dto);

    // Get all chats for a user
    List<ChatDTO> getChatsForUser(Long userId);
}
