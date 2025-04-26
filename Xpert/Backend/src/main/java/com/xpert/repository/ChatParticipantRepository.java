package com.xpert.repository;

import com.xpert.entity.chat.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {

    // Get all participants for a given chat
    List<ChatParticipant> findByChatId(Long chatId);

    // Get all chats a user is participating in
    List<ChatParticipant> findByUserId(Long userId);

    // Check if a user is already part of a chat
    boolean existsByChatIdAndUserId(Long chatId, Long userId);
}
