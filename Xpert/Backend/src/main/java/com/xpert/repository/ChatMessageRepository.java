package com.xpert.repository;


import com.xpert.entity.chat.ChatMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Get all messages for a given chat (sorted by time)
    List<ChatMessage> findByChatIdOrderByCreatedAtAsc(Long chatId);

     // Get the latest message (useful for lastMessageAt updates)
    ChatMessage findFirstByChatIdOrderByCreatedAtDesc(Long chatId);
}
